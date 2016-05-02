(function() {
  var gallery = document.querySelector(".products-grid");

  window.addEventListener("load", function() {
    cng.item.load({});
  });

  cng.item = {};

  /**
   * Loading items into grid.
   */
  cng.item.load = function(query) {
    gallery.innerHTML = "";
    $.ajax({
      contentType: 'application/json',
      data: JSON.stringify(query),
      dataType: 'json',
      success: function(data){
          createList(JSON.stringify(data));
      },
      error: function(){
          console.error("Error cng.item.load");
      },
      processData: false,
      type: 'POST',
      url: '/item/find'
    });

  }

  function isEmpty(obj) {
    return Object.keys(obj).length === 0 && JSON.stringify(obj) === JSON.stringify({});
  }

  /**
   * Processes response, containing String-represented Json.
   * @var response {Object}
   */
  function createList(response) {
    var list = JSON.parse(response);
    for (var i = 0; i < list.length; i++) {
      create(list[i]);
    }
  }

  /**
   * Processes item's object.
   * @var item {Object}
   */
  function create(item) {
    var itemLi = document.createElement("li");
    itemLi.style.display = 'none';
    itemLi.className = "item";

    var itemInnerDiv = document.createElement("div");
    itemInnerDiv.className = "item-inner";
    itemInnerDiv.id = item._id.$oid;

    var productImageA = document.createElement("a");
    productImageA.className = "product-image";
    productImageA.href = item.url;
    productImageA.title = item.title;

    var productImageImg = document.createElement("img");
    productImageImg.className = "img";
    productImageImg.src = "/assets/images/test-phone.jpg";
    productImageImg.alt = item.title;

    var productContainerAll = document.createElement("div");
    productContainerAll.className = "product-container-all";

    var productNameContainer = document.createElement("p");
    productNameContainer.className = "product-name-container";

    var productName = document.createElement("a");
    productName.className = "product-name";
    productName.href = item.url;
    productName.title = item.title;
    productName.innerText = item.title;

    var category = document.createElement("div");
    category.className = "category";
    category.innerText = item.category;

    var subCategory = document.createElement("div");
    subCategory.className = "sub-category";
    subCategory.innerText = item.subcategory;

    var grid = document.createElement("div");
    grid.className = "grid";

    var deliveryPeriod = document.createElement("div");
    deliveryPeriod.className = "delivery-period"

    var deliveryText = document.createElement("div");
    deliveryText.className = "delivery-text";
    deliveryText.innerText = "Срок доставки 2-5 дней";

    var deliveryIcon = document.createElement("div");
    deliveryIcon.className = "delivery-icon";

    var deliveryIconCar = document.createElement("span");
    deliveryIconCar.className = "icon delivery-icon-car";
    deliveryIconCar.title = "Курьерская доставка";

    var buyBox = document.createElement("div");
    buyBox.className = "buy-box";

    var priceBox = document.createElement("div");
    priceBox.className = "price-box";

    var regularPrice = document.createElement("span");
    regularPrice.className = "regular-price";

    var priceWrapper = document.createElement("span");
    priceWrapper.className = "price price-sym-7";

    var sum = document.createElement("span");
    sum.className = "sum";
    sum.innerText = item.price;

    var currency = document.createElement("span");
    currency.className = "currency";
    currency.innerText = " грн.";

    var attributesWrapper = document.createElement("div");
    attributesWrapper.className = "attributes por";

    var infoPopup = document.createElement("div");
    infoPopup.className = "info-popup";

    var content = document.createElement("div");
    content.className = "content";

    var attrContainer = document.createElement("div");
    attrContainer.className = "attr-container";

    var attrContent = document.createElement("div");
    attrContent.className = "attr-content";

    /**
     * If item's features object is not empty - display corresponding view on page.
     */
    if (!isEmpty(item.features)) {
      var featuresHolder = document.createElement("p");
      for (var feature in item.features) {
        var key = document.createElement("p");
        key.className = "span1";
        key.innerText = feature;

        var value = document.createElement("span");
        value.className = "span2";
        value.innerText = item.features[feature];

        featuresHolder.appendChild(key);
        featuresHolder.appendChild(value);
      }
        attrContent.appendChild(featuresHolder);
        attrContainer.appendChild(attrContent);
        content.appendChild(attrContainer);
        infoPopup.appendChild(content);
        attributesWrapper.appendChild(infoPopup);
    }

    priceWrapper.appendChild(sum);
    priceWrapper.appendChild(currency);
    regularPrice.appendChild(priceWrapper);
    priceBox.appendChild(regularPrice);
    buyBox.appendChild(priceBox);

    deliveryIcon.appendChild(deliveryIconCar);
    deliveryPeriod.appendChild(deliveryText);
    deliveryPeriod.appendChild(deliveryIcon);
    grid.appendChild(deliveryPeriod);

    productNameContainer.appendChild(productName);
    productContainerAll.appendChild(productNameContainer);

    productImageA.appendChild(productImageImg);

    itemInnerDiv.appendChild(productImageA);
    itemInnerDiv.appendChild(productContainerAll);
    itemInnerDiv.appendChild(category);
    itemInnerDiv.appendChild(subCategory);
    itemInnerDiv.appendChild(grid);
    itemInnerDiv.appendChild(buyBox);
    itemInnerDiv.appendChild(attributesWrapper);

    itemLi.appendChild(itemInnerDiv);

    gallery.appendChild(itemLi);

    window.setTimeout(function() {
      $(itemLi).fadeIn(400);
    });
  }
}());

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
        obj = obj.labels.length > 0 ? obj.labels[0] : obj;
        var _coordinates = obj.getBoundingClientRect();
        var _top = _coordinates.top;
        var _right = _coordinates.right;

        this.obj.style.top = _top + "px";
        this.obj.style.left = _right + 10 + "px";

        this.display();
      }
    };

    refreshBlock.obj = document.createElement("div");
    refreshBlock.obj.classList.add("refresh-block");
    refreshBlock.obj.classList.add("refresh-block-visible");
    refreshBlock.hide();
    cng.filters.form.parentNode.appendChild(refreshBlock.obj);

    refreshBlock.obj.addEventListener("click", function(ev) {
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
      cng.item.load(this.query);
    }
  };

  cng.filters.bindTo("filters");
}());

/**
* Toggle
*/
(function() {
  $('.cd-filter-trigger').on('click', function(){
    triggerFilter(true);
  });
  $('.cd-filter .cd-close').on('click', function(){
    triggerFilter(false);
  });

  function triggerFilter($bool) {
    var elementsToTrigger = $([$('.cd-filter-trigger'), $('.cd-filter'), $('.cd-tab-filter'), $('.cd-gallery')]);
    elementsToTrigger.each(function(){
      $(this).toggleClass('filter-is-visible', $bool);
    });
  }

  //close filter dropdown inside lateral .cd-filter
  $('.cd-filter-block h4').on('click', function(){
    $(this).toggleClass('closed').siblings('.cd-filter-content').slideToggle(300);
  })
}());
