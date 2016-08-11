(function() {
  if (!window.cng) {
    window.cng = {};
  }
  window.cng.editable = {
    init: function() {
      var a = document.getElementsByClassName('edit');
      for (var i = 0; i < a.length; i++) {
        a[i].addEventListener("click", editInput, false);
      }
    }
  };

  function editInput(event) {
    if (event.target.tagName === "INPUT" || event.target.tagName === "BUTTON" ) {
      return false;
    }

    var li = this;
    var input = document.createElement("input");
    input.type = "text";
    input.value = this.innerText;
    var oldName = this.innerText;
    this.innerText= "";
    this.appendChild(input);
    addSaveButton();
    addCancelButton();
    input.onfocus = function () {
      this.onmousedown = function() {
        if (event.parentNode) {
          if (event.parentNode.tagName === "BUTTON") {
            return false;
          }
          this.parentNode.innerText = this.value;
          this.remove();
        }
      };
    };
    input.focus();

    function addSaveButton() {
        var button = document.createElement("button");
        button.innerText = "Save";
        button.addEventListener("click", function() {
            li.innerHTML = input.value;
        }, true);
        li.appendChild(button);
        button.addEventListener("click", function() {
            var newName = input.value;
            cng.ajax.put("/admin/categories/modify/" + oldName + "/" + newName, function() {
                b.setAttribute("style", "font-weight:normal");
            });
            var b = li;
            b.setAttribute("style", "font-weight:bold")
        }, false);
    }

      function addCancelButton() {
        var button = document.createElement("button");
        button.innerText = "Cancel";
        button.onclick = function () {
          li.innerHTML = "";
          li.innerText = oldName;
          return false;
        }
        li.appendChild(button);
      }
    }
  })();
