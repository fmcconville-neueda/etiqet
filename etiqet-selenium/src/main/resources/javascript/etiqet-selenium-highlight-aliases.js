function sleep(ms){
    return new Promise(resolve => setTimeout(resolve, ms));
}

(async function(callback){
    var options = {
        trigger: 'manual'
    }
    $('[etiqet-alias]').each(function(){
        options.title = $(this).attr('etiqet-alias'),
        $(this).tooltip(options).tooltip('show');
    });
    await sleep(150);  // Waiting on fade finishing
    callback();
})(arguments[arguments.length - 1]);
