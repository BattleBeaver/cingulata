window.onload = function() {
  cng.editable.init();
  
  document.querySelector("#sidebar-menu").addEventListener("click", function() {
    if (event.target.tagName == "SPAN") {

      // if (event.target.id == "categories") {
      //   cng.ajax.get("admin/categories", function(xhr) {
      //     document.getElementById("page-content").innerHTML = xhr.response;
      //     cng.editable.init();
      //   });
      //
      // } else
      if (event.target.id == "crawlers") {
        cng.ajax.get("admin/crawlers", function(xhr) {
          document.getElementById("page-content").innerHTML = xhr.response;
          cng.editable.init();
          manageCrawlers();

        });

      } else if (event.target.id == "crawler-details") {
          cng.ajax.get("admin/crawler-details", function(xhr) {
            document.getElementById("page-content").innerHTML = xhr.response;
            cng.editable.init();
          });
      }

    }
  })

    function manageCrawlers() {
      var table = document.querySelector("table");
      var tbody = document.querySelector("tbody");
      var thead = document.querySelector("thead");
      var headerRow = thead.firstElementChild;
      var th = headerRow.querySelectorAll("th");
      headerRow.addEventListener("mouseover", function() {
        for (var i = 0; i < th.length; i++) {
          thead.style.color = "#f87500";
        }
      });
      headerRow.addEventListener("mouseout", function() {
          for (var i = 0; i < th.length; i++) {
            thead.style.color = "black";
            thead.style.fontWeight = "normal";
      }
      });

      tbody.addEventListener("mouseover", function() {
        console.log(event.target);
        if (event.target.tagName == "TD") {
          console.log(event.target.parentNode);
          var currentElement = event.target.parentNode.querySelectorAll("td");
          event.target.parentNode.style.boxShadow = "0 2px 18px rgba(0,0,0,0.25), 0 1px 5px rgba(0,0,0,0.22)";
          event.target.parentNode.style.cursor = "pointer";
        }
      });

      tbody.addEventListener("mouseout", function() {
        console.log(event.target);
        if (event.target.tagName == "TD") {
          console.log(event.target.parentNode);
          var currentElement = event.target.parentNode.querySelectorAll("td");
          event.target.parentNode.style.boxShadow = "0 1px 3px rgba(0,0,0,0.12), 0 1px 2px rgba(0,0,0,0.24)";
        }
      })

    }
  }

  function verifyCrawler() {
    var obj = {
      form: document.forms[0],
      url: "http://localhost:9001/crawlers/verify",
      type: "json"
    };

    cng.ajax.submit(obj);
  }

  function deleteCrawler(id) {
    cng.ajax.delete("admin/crawlers/" + id + "/delete");
  }
