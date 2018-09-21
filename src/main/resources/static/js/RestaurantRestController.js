var RestaurantRestController = (function (){
    var url = '';

    function getOrders() {
        return axios.get(url + "/orders").then(function (response) {
            return response.data;
        })
    }

    function getProducts() {
        return axios.get(url + "/orders/products").then(function (response) {
            return response.data;
        })
    }
    
    function updateOrder(orderId,quantity) {
        axios.put(url + "/orders/"+orderId+"/"+quantity, product);
    }
    
    function deleteOrder(orderId,itemName) {
        axios.delete(url + "/orders/"+orderId+"/"+itemName);
    }
    
    function addDish(orderId,quantity) {
        axios.post(url + "/orders/"+orderId+"/"+quantity, product);
    }
    
    
    
    return {
        getOrders: getOrders,
        getProducts: getProducts,
        updateOrder: updateOrder,
        deleteOrder: deleteOrder
    };
})();