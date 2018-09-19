var OrdersController = (function () {
    
    var numberTable = 1;
    
    function clearTables() {
        var tables = document.getElementsByClassName("table");
        for (i = 0; i < tables.length; i++) {
            tables[i].innerHTML = "";
        }
        numberTable = 1;
    }

    function addOrder(order) {
        var tableOrder = document.createElement("table");
        var header = document.createElement("tr");
        var cell = document.createElement("th");
        cell.innerHTML = "Product";
        header.appendChild(cell);
        var cell = document.createElement("th");
        cell.innerHTML = "Quantity";
        header.appendChild(cell);
        var cell = document.createElement("th");
        cell.innerHTML = "Price";
        header.appendChild(cell);
        tableOrder.appendChild(header);
        tableOrder.setAttribute("class", "table");
        for (var i = 0; i < order.products.length; i++) {
            var row = document.createElement("tr");

            var cell = document.createElement("td");
            cell.innerHTML = order.products[i].product;
            row.appendChild(cell);

            var cell = document.createElement("td");
            cell.innerHTML = order.products[i].quantity;
            row.appendChild(cell);

            var cell = document.createElement("td");
            cell.innerHTML = order.products[i].price;
            row.appendChild(cell);

            tableOrder.appendChild(row);
        }
        var title = document.createElement("h4");
        title.setAttribute("class","cover-heading");
        title.innerHTML = "Table "+numberTable++;
        var content = document.getElementById("content");
        var firstTable = document.getElementById("HTMLtable");
        content.insertBefore(tableOrder,firstTable);
        content.insertBefore(title,tableOrder);
    }

    function loadOrders() {
        clearTables();
        loadData(ordersBuilder);
    }

    function loadData(callback) {
        axios.all([RestaurantRestController.getOrders(), RestaurantRestController.getProducts()])
                .then(axios.spread(function (orders, products) {
                    callback(orders, products);
                }));
    }



    function ordersBuilder(orders, products) {
        for (i in orders) {
            orderBuilder(orders[i], products)
        }
    }

    function orderBuilder(orderJson, products) {
        var order = {order_id: orderJson.tableNumber, table_id: orderJson.tableNumber, products: []};
        for (productName in orderJson.orderAmountsMap) {
            var prod = {"product": productName, "quantity": orderJson.orderAmountsMap[productName], "price": null}
            prod.price = products.filter(function (x) {
                return x.name === productName;
            })[0].price;
            order.products.push(prod);
        }
        addOrder(order);
    }
    return {
        loadOrders: loadOrders
    };
})();