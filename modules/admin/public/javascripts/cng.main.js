//Ajax processing
(function() {
    if (!window.cng) {
        window.cng = {};
    }

    /**
     * Processes request.
     * @var method {String}
     * @var url {String}
     * @var callback {Function}
     */
    function request(method, url, callback) {
        var xhttp = new XMLHttpRequest();
        xhttp.open(method, url, true);

        xhttp.onreadystatechange = function(e) {
            if (callback) {
              if (this.readyState === 4 && this.status === 200) {
                  callback(this);
              }
            }
        };
        xhttp.send();
    }

    /**
     * Serializes form.
     * @var {Node}
     */
    function serialize(form, type) {
        if (type && type == "json") {
            var data = {};
            var currObj = {};
            for (var i = 0, ii = form.length; i < ii; ++i) {
                var input = form[i];
                if (input.name) {
                    var destructed = input.name.split(".");
                    switch (destructed.length) {
                        case 1:
                            data[input.name] = input.value;
                            break;
                        case 2:
                            nestedObj(destructed, input);
                            break;
                        case 3:
                          doubleNestedObj(destructed, input);

                    }
                }
            }
            console.log(data);
            return JSON.stringify(data);

        }

        function nestedObj(destructed, input) {
            for (var i = 0; i < destructed.length - 1; i++) {
                //if doesn`t exist this keys in object DATA - create
                if (!data[destructed[0]]) {
                  if (Object.keys(currObj).length !== 0) {
                      currObj = {};
                  }
                    var nested = data[destructed[0]] = {};
                }

                //if last keys equal to current value(input name which have nested elements)
                if (Object.keys(data)[Object.keys(data).length - 1] == destructed[0]) {
                    currObj[destructed[1]] = input.value;
                }
                //if object doesn`t empty - add that content in object
                if (Object.keys(currObj).length !== 0) {
                    for (var key in currObj) {
                        var lastKey = data[Object.keys(data)[Object.keys(data).length - 1]];
                        lastKey[key] = currObj[key];
                    }
                }
            }
        }

        function doubleNestedObj(destructed, input) {
          for (var i = 0; i < destructed.length - 1; i++) {
              //if doesn`t exist this keys in object DATA - create
              if (!data[destructed[0]]) {
                if (Object.keys(currObj).length !== 0) {
                    currObj = {};
                }
                  var nested = data[destructed[0]] = {};
              }

                //if in the nested Object doesn`t exist needed Object - create
                var allValue = Object.keys(data[destructed[0]]);

                function indent(val) {
                  return val != destructed[1]
                }
                // use ES5 function every
                 if(allValue.every(indent))
                {
                    var val = data[destructed[0]][destructed[1]] = {};
                    if (Object.keys(currObj).length !== 0) {
                        currObj = {};
                    }
                  }

              currObj[destructed[2]] = input.value;

                // if  current object doesn`t empty - add that content in nested object
                if (Object.keys(currObj).length !== 0) {
                    for (var key in currObj) {
                      if (currObj.hasOwnProperty(key)) {
                        var lastKey = data[Object.keys(data)[Object.keys(data).length-1]]
                        data[destructed[0]][destructed[1]][key] = currObj[key];
                      }
                    }
                }
            }
        }
    }


    /**
     * Takes a form node and sends it over AJAX.
     * @param {HTMLFormElement} form - Form node to send
     * @param {function} callback - Function to handle onload.
     *                              this variable will be bound correctly.
     */
    function submit(opts) {
        var xhttp = new XMLHttpRequest();
        xhttp.open("POST", opts.url || form.action, true);

        xhttp.setRequestHeader("Content-type", opts.type && opts.type == "json" ? "application/json" : "application/x-www-form-urlencoded");

        xhttp.send(serialize(opts.form, opts.type));
    }

    /**
     * Ajax functions wrapper. POST, GET, PUT, DELETE supported.
     * @var url {String}
     * @var callback {Function}
     */
    window.cng.ajax = {
        get(url, callback) {
            request("GET", url, callback);
        },
        put(url, callback) {
            request("PUT", url, callback);
        },
        post(url, callback) {
            request("POST", url, callback);
        },
        delete(url, callback) {
            request("DELETE", url, callback);
        },
        submit(opts) {
            submit(opts);
        }
    }
}())
