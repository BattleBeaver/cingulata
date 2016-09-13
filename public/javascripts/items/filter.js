/**
* Filters
*/
(function() {

  //creates a block for refreshing gallery.
  function createRefreshBlock() {
    var refreshBlock = {
      obj : null,

      display : function() {
        this.obj.style.display = "block";
      },

      hide : function() {
        this.obj.style.display = "none";
      },

      moveTo : function(obj) {
        var _this = this;
        obj = obj.labels.length > 0 ? obj.labels[0] : obj;
        var _coordinates = obj.getBoundingClientRect();
        var _top = _coordinates.top;
        var _right = _coordinates.right;

        this.obj.style.top = _top + "px";
        this.obj.style.left = _right + 10 + "px";

        window.setTimeout(function() {
          _this.hide();
        }, 2000);

        this.display();
      }
    };

    refreshBlock.obj = document.createElement("div");
    refreshBlock.obj.classList.add("refresh-block");
    refreshBlock.obj.classList.add("refresh-block-visible");
    refreshBlock.hide();
    cng.filters.form.parentNode.appendChild(refreshBlock.obj);

    refreshBlock.obj.addEventListener("click", function(ev) {
      cng.item.count(cng.filters.query);
      var a = document.createElement("div");
      a.classList.add("summary");;
      cng.filters.applyAndLoad();
    });

    return refreshBlock;
  }

  cng.filters = {
    query : {},

    bindTo : function(formId) {
      var _self = this;

      _self.form = document.getElementById(formId);
      _self.form.addEventListener("change", function(ev) {
        if (ev.srcElement.type == "checkbox") {
          (ev.srcElement.checked ? _self.add : _self.remove).call(_self, ev.srcElement)
        } else if (ev.srcElement.type == "search") {
          _self.set(ev.srcElement);
        }

        _self.refreshBlock.moveTo(ev.srcElement);
      });

      _self.refreshBlock = createRefreshBlock();
    },

    add : function(element) {
      if (!this.query[element.name]) {
        this.query[element.name] = [];
      }
      this.query[element.name].push(element.value);
    },

    set : function(element) {
      this.query[element.name] = element.value;
    },

    remove : function(element) {
      this.query[element.name].splice(this.query[element.name].indexOf(element.id), 1);
      if (this.query[element.name].length == 0) {
        delete this.query[element.name]
      }
    },

    applyAndLoad : function() {
      var summarySearch = document.querySelector(".summary");
      summarySearch.innerHTML = "items found";
      var summarySearch = document.querySelector(".summary");
      var that = this;
      var temporaryCount = cng.item.count(this.query);
      page = 0;
      block = false;
      cng.item.clearGrid();
      cng.item.load(this.query);
      summarySearch.addEventListener("mouseover", function() {
        this.innerHTML = "show all";
      });
      summarySearch.addEventListener("mouseleave", function() {
        cng.item.count(that.query);
      });
    },

    count : function() {
      cng.item.count(this.query);
      window.addEventListener("scroll", addNewEntry, false);
    }
  };

  function loadGroupedCategories() {
    cng.ajax.get("/admin/categories/groupings", function (loader) {
      var array = JSON.parse(loader.response);
      buildCategoryTree(array);
      buildSubCategoryTree(array);
    });
  }

  function buildCategoryTree(objArray) {
    var root = document.getElementById("categories-root");

    var ul = document.createElement("UL");
    ul.className = "cd-filter-content cd-filters list";

    root.appendChild(ul);

    var categories = objArray.map(function(obj) {
      return obj.category;
    });

    for (var i = 0; i < categories.length; i++) {
      //mock
      if (i == 7) break;

      var categoryText = categories[i];

      var categoryLi = document.createElement("li");

      var input = document.createElement("input");
      input.className = "filter";
      input.type = "checkbox";
      input.id = categoryText;
      input.value = categoryText;
      input.name = "category";

      var label = document.createElement("LABEL");
      label.className = "checkbox-label";
      label.htmlFor = categoryText;
      label.innerText = categoryText;

      categoryLi.appendChild(input);
      categoryLi.appendChild(label);

      ul.appendChild(categoryLi);
    }
  }

  function buildSubCategoryTree(objArray) {
    var unique = function(xs) {
      return xs.filter(function(x, i) {
        return xs.indexOf(x) === i
      })
    }

    var root = document.getElementById("sub-categories-root");

    var ul = document.createElement("UL");
    ul.className = "cd-filter-content cd-filters list";

    root.appendChild(ul);

    var subcategories = [];
    objArray.forEach(function(a) {
      a.subcategories.forEach(function(b) {
        subcategories.push(b)
      })
    });
    subcategories = unique(subcategories);
    for (var i = 0; i < subcategories.length; i++) {
      //mock
      if (i == 7) break;

      var categoryText = subcategories[i];

      var categoryLi = document.createElement("li");

      var input = document.createElement("input");
      input.className = "filter";
      input.type = "checkbox";
      input.id = categoryText;
      input.value = categoryText;
      input.name = "subcategory";

      var label = document.createElement("LABEL");
      label.className = "checkbox-label";
      label.htmlFor = categoryText;
      label.innerText = categoryText;

      categoryLi.appendChild(input);
      categoryLi.appendChild(label);

      ul.appendChild(categoryLi);
    }
  }

  cng.filters.bindTo("filters");
  loadGroupedCategories();
}());
