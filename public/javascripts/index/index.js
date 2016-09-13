window.addEventListener("load", function() {
  [].forEach.call(document.querySelectorAll("button"), function(button) {
    button.addEventListener("click", function() {
      window.open("/items/index");
    }, false)
  });

  var data = {
    labels: ["Phones", "Laptops", "Engines", "Accessories", "Travel", "Sport", "Other"],
    datasets: [
        {
            label: "Last month",
            backgroundColor: "rgba(179,181,198,0.2)",
            borderColor: "rgb(165, 234, 215)",
            pointBackgroundColor: "rgba(179,181,198,1)",
            pointBorderColor: "#fff",
            pointHoverBackgroundColor: "#fff",
            pointHoverBorderColor: "rgba(179,181,198,1)",
            data: [65, 59, 90, 81, 56, 55, 40]
        },
        {
            label: "Current month",
            backgroundColor: "rgba(255,99,132,0.2)",
            borderColor: "rgba(255,99,132,1)",
            pointBackgroundColor: "rgba(255,99,132,1)",
            pointBorderColor: "#fff",
            pointHoverBackgroundColor: "#fff",
            pointHoverBorderColor: "rgba(255,99,132,1)",
            data: [28, 48, 40, 19, 96, 27, 100]
        }
    ]
};

  window.chart = new Chart(document.getElementById("chart").getContext("2d"), {
    type: 'radar',
    data: data
  });
});
