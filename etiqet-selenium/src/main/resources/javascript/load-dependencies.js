function require(file, callback) {
    callback = callback ||
    function () {};
    var filenode;
    var jsfile_extension = /(.js)$/i;
    var cssfile_extension = /(.css)$/i;

    if (jsfile_extension.test(file)) {
        filenode = document.createElement('script');
        filenode.src = file;
        filenode.onreadystatechange = function () {
            if (filenode.readyState === 'loaded' || filenode.readyState === 'complete') {
                filenode.onreadystatechange = null;
                callback();
            }
        };
        filenode.onload = function () {
            callback();
        };
        document.head.appendChild(filenode);
    } else if (cssfile_extension.test(file)) {
        filenode = document.createElement('link');
        filenode.rel = 'stylesheet';
        filenode.type = 'text/css';
        filenode.href = file;
        document.head.appendChild(filenode);
        callback();
    } else {
        console.log("Unknown file type to load.")
    }
};

var requireFiles = function () {
    var index = 0;
    return function (files, callback) {
        index += 1;
        console.log("loading "+files[index - 1]);
        require(files[index - 1], callBackCounter);

        function callBackCounter() {
            if (index === files.length) {
                index = 0;
                callback();
            } else {
                requireFiles(files, callback);
            }
        };
    };
}();

(function(dependencies, callback){
    return requireFiles(dependencies, function(){
        return callback();
    });
})(arguments[0], arguments[arguments.length -1]);

