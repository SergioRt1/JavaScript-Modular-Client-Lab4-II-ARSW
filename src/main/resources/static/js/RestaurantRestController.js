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
    
    
    return {
        getOrders: getOrders,
        getProducts: getProducts
    };
})();