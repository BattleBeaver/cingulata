var page = 0;
var block = false;

// Scroll and loading new items
function addNewEntry() {
  var filter = document.querySelector(".cd-filter-trigger");
  var items = document.querySelector(".cd-gallery");
  var filterVisible = document.querySelector(".filter-is-visible");
  var lastItems = document.querySelector(".lastItems");
    if (items.clientHeight < 700) {
      var pos = lastItems.getBoundingClientRect().top;
      if (pos < 500 && !block) {
        // block = true;
          cng.item.load(cng.filters.query);
      }
    }
    else {
      var result = parseInt(items.clientHeight) - parseInt(this.pageYOffset);
      if (result < window.innerHeight && !block) {
        page++;
        var pager = document.querySelector(".pager");
        pager.innerHTML = page;
        block = true;
        cng.item.load(cng.filters.query);
  }
    }
};

(function() {
  var gallery = document.querySelector(".products-grid");
  window.addEventListener("load", function() {
    cng.item.load(cng.filters.query);
  });

    window.addEventListener("scroll", addNewEntry, false);


  cng.item = {};

  /**
  * Loading items into grid.
  */
  cng.item.load = function(query) {
    $.ajax({
      contentType: 'application/json',
      data: JSON.stringify(query),
      dataType: 'json',
      success: function(data){
        createList(JSON.stringify(data));
        block = false;
      },
      error: function(){
        console.error("Error cng.item.load");
      },

      processData: false,
      type: 'POST',
      url: '/item/find?page=' + page,
      beforeSend: function () {
        $("#loader").css("display", "block");
      },
      complete: function () {
        window.setTimeout(function() {$("#loader").css("display", "none")}, 500)
      }
    });
  }
  cng.item.count = function(query) {
    $.ajax({
      contentType: 'application/json',
      data: JSON.stringify(query),
      dataType: 'json',
      success: function(data){
        var summarySearch = document.querySelector(".summary");
        summarySearch.innerText = data+" "+"items found";
        summarySearch.style.color = "#41307C";
        summarySearch.style.fontSize = "12px";
      },
      error: function(){
          console.error("Error cng.item.count");
      },
      processData: false,
      type: 'POST',
      url: '/item/count',
      complete: function () {
      }
    });
  }

  cng.item.clearGrid = function(query) {
    gallery.innerHTML = "";
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
    if (list.length === 0) {
      window.removeEventListener("scroll", addNewEntry);
      return false;
    }
    for (var i = 0; i < list.length; i++) {
      create(list[i],list[list.length-1]._id.$oid);

    }
    var lastItems = list[list.length-1];
    };

  /**
  * Processes item's object.
  * @var item {Object}
  */
  function create(item,lastItems) {
    var itemLi = document.createElement("li");
    itemLi.style.display = 'none';
    itemLi.className = "item";
    if (lastItems) {
      if (item._id.$oid === lastItems) {
        var li = document.querySelectorAll("li")
        for (var i = 0; i < li.length; i++) {
          li[i].classList.remove("lastItems");
        }
        itemLi.classList.add("lastItems");
      }
    }


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
 * Toggle
 */
(function() {

    $('.cd-filter-trigger').on('click', function() {
        var elementsToTrigger = $([$('.cd-filter-trigger'), $('.cd-filter'), $('.cd-tab-filter'), $('.cd-gallery')]);
        elementsToTrigger.each(function() {
            $(this).toggleClass('filter-is-visible');
        });
        this.style.backgroundColor = "transparent";
    });
    $('.filter-is-visible').on('click', function() {
        this.style.backgroundColor = "transparent";
    });
    $('.cd-filter .cd-close').on('click', function() {});
    $('.cd-filter-block h4').on('click', function() {
        $(this).toggleClass('closed').siblings('.cd-filter-content').slideToggle(300);
    })
}());
