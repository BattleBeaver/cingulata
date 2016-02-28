(function() {
  window.cng = {};
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
    var input = document.createElement("input")
    input.type = "text"
    input.value = this.innerText;
    var oldName = this.innerText;
    this.innerText= "";
    this.appendChild(input);
    addSaveButton();
    addCancelButton();
    input.onfocus = function () {
      this.onmousedown = function() {
        if (event.parentNode) {
          if (event.parentNode.tagName == "BUTTON") {
            return false;
          }
          this.parentNode.innerText = this.value;
          this.remove();
        }
      }
    }
    input.focus();

    function addSaveButton() {
      var button = document.createElement("button");
      button.innerText = "Save";
      button.addEventListener("click" , function() {
        li.innerHTML = input.value;
      }, true);
      li.appendChild(button);

      button.addEventListener("click", function(e) {
        var newName = input.value;
        var xhttp = new XMLHttpRequest();
        xhttp.onreadystatechange = function() {

          if  (xhttp.status == 200) {
            console.log(xhttp.responseText);
            b.setAttribute("style", "font-weight:normal")
          }
        }
        xhttp.open("GET", "/categories/modify/" + oldName +  "/" + newName, true);
        xhttp.send();
        var b = li;
        b.setAttribute("style", "font-weight:bold")
      },true)
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

  window.addEventListener("load", cng.editable.init, false);
})()
