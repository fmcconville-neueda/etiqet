package com.neueda.etiqet.selenium.fixture;

import com.neueda.etiqet.core.common.exceptions.EtiqetException;
import com.neueda.etiqet.core.util.StringUtils;
import com.neueda.etiqet.selenium.fixture.annotations.SearchMethod;
import org.openqa.selenium.*;
import org.reflections.Reflections;
import org.reflections.scanners.MethodAnnotationsScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

/** Singleton which holds a reference of methods annotated with @SearchMethod */
public class WebElementSearchMethods {
    private static WebElementSearchMethods instance;
    private Map<String, Method> searchMethods;

    /** fill searchMethods map with methods annotated with @SearchMethod */
    private WebElementSearchMethods() {
        Reflections reflections = new Reflections(
                new ConfigurationBuilder().setUrls(
                        ClasspathHelper.forPackage( "com.neueda.etiqet.selenium.fixture" ) ).setScanners(
                        new MethodAnnotationsScanner()
                )
        );
        searchMethods = new HashMap<>();
        for(Method method: reflections.getMethodsAnnotatedWith(SearchMethod.class)){
            searchMethods.put(method.getAnnotation(SearchMethod.class).value(), method);
        }
    }

    public static WebElementSearchMethods getInstance(){
        if (instance == null){
            instance = new WebElementSearchMethods();
        }
        return instance;
    }

    public WebElement search(SeleniumHandlers handlers, String occurrence, String elementType, String attributes) throws EtiqetException{
        return new SearchQuery(handlers, occurrence, elementType, attributes).getResult();
    }

    public class SearchQuery {
        private String elementType;
        private int occurrence;
        private Map<String, String> searchAttributes = new HashMap<>();
        private SeleniumHandlers handlers;
        private List<WebElement> results = null;

        public SearchQuery(SeleniumHandlers handlers, String occurrence, String elementType, String attributes) throws EtiqetException{
            this.handlers = handlers;
            this.elementType = elementType;

            try {
                this.occurrence = Integer.parseInt(occurrence);
            } catch (NumberFormatException e){
                throw new EtiqetException(String.format("occurrence '%s' is not a valid integer", occurrence));
            }

            if (!StringUtils.isNullOrEmpty(attributes)){
                for (String attributePair: attributes.split(",")){
                    String [] keyValueArray = attributePair.split("=");
                    searchAttributes.put(keyValueArray[0].trim(), keyValueArray[1].trim());
                }
            }
        }

        private String getAttributeString(){
            String result = "";
            for (String attribute: searchAttributes.keySet())
                result += String.format("%s=%s, ", attribute, searchAttributes.get(attribute));
            return result.substring(0, result.length() - 2);
        }

        public void joinWithResults(List<WebElement> elements) throws EtiqetException{
            if (results == null){
                results = elements;
            } else {
                List<WebElement> toRemove = new ArrayList<>();
                for (WebElement element: results){
                    if (!elements.contains(element))
                        toRemove.add(element);
                }

                for (WebElement element: toRemove){
                    results.remove(element);
                }
            }
            if (results.size() == 0) {
                throw new EtiqetException(String.format("Occurrence '%s' of Element type '%s' with searchAttributes '%s' not found", occurrence, elementType, getAttributeString()));
            }
        }

        private List<WebElement> attributeSearch(String attribute, String value) throws EtiqetException{
            boolean not = false;
            String attributeQuery;

            if (attribute.charAt(attribute.length() - 1) == '!'){
                not = true;
                attribute = attribute.substring(0, attribute.length() -1);
            }

            if (searchMethods.containsKey(attribute)){
                try{
                    return (List<WebElement>) searchMethods.get(attribute).invoke(null, handlers, value, elementType, not);
                } catch (IllegalAccessError | IllegalAccessException | InvocationTargetException e) {
                    throw new EtiqetException(e);
                }
            } else {
                attributeQuery = String.format("@%s='%s'", attribute, value);
                if (not) {
                    attributeQuery = String.format("not(%s)", attributeQuery);
                }
                return handlers.getDriver().findElements(By.xpath(String.format("//%s[%s]", elementType, attributeQuery)));
            }
        }

        public WebElement getResult() throws EtiqetException{
            if (searchAttributes.size() == 0){
                joinWithResults(handlers.getDriver().findElements(By.xpath(String.format("//%s", elementType))));
            } else {
                for (String attribute: searchAttributes.keySet()){
                    joinWithResults(attributeSearch(attribute, searchAttributes.get(attribute)));
                }
            }
            return results.get(occurrence - 1);
        }

        public Map<String,String> getSearchAttributes(){
            return searchAttributes;
        }
    }

    @SearchMethod("text-icontains")
    public static List<WebElement> searchByTextInsensitiveContains(SeleniumHandlers handlers, String value, String elementType, boolean not) throws EtiqetException {
        String query = String.format("contains(translate(text(),'abcdefghijklmnopqrstuvwxyz','ABCDEFGHIJKLMNOPQRSTUVWXYZ'), '%s')", value.toUpperCase());
        if (not){
            query = String.format("not(%s)", query);
        }
        query = String.format("//%s[%s]", elementType, query);
        return handlers.getDriver().findElements(By.xpath(query));
    }

    @SearchMethod("child-of")
    public static List<WebElement> searchForChilderenOf(SeleniumHandlers handlers, String value, String elementType, boolean not) throws EtiqetException{
        handlers.getNamedWebElement(value); // Throw exception if alias can't be found;
        String query = String.format("@etiqet-alias='%s'", value);
        if (not){
            query = String.format("not(%s)", query);
        }
        return handlers.getDriver().findElements(By.xpath(String.format("//*[%s]//%s", query, elementType)));
    }

    @SearchMethod("input-val()")
    public static List<WebElement> searchForInputWithValue(SeleniumHandlers handlers, String value, String elementType, boolean not) throws EtiqetException{
        List<WebElement> results = new ArrayList<>();
        for (WebElement element: handlers.getDriver().findElements(By.xpath("//input"))){
            if (element.getAttribute("value").equals(value)){
                results.add(element);
            } else if (not){
                results.add(element);
            }
        }
        return results;
    }

}
