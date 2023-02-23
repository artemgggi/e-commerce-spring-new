function handleClickAddProduct(event)
{
  event.preventDefault();
  fire_ajax();
}
function  fire_ajax()
{
	$.ajax({
        type: "GET",
        url: "/admin/products",
        processData: false,
        contentType: false,
        cache: false,
        timeout: 600000,
        success: function (data) {
        	$("#app").empty();
        	$("#app").append(data);
        },
    error: function (e) {
        console.log("Error: ", e)
    }
    });
};


function handleClickProductList(event) {
  event.preventDefault();
  fire_ajax1();
}
function  fire_ajax1() {
	$.ajax({
        type: "GET",
        url: "/admin/productList",
        processData: false,
        contentType: false,
        cache: false,
        timeout: 600000,
        success: function (data) {
        	$("#app").empty();
        	$("#app").append(data);
        },
    error: function (e) {
        console.log("Error: ", e)
    }
    });
}


function handleClickLogin(event) {
  event.preventDefault();
  fire_ajax2();
}

function  fire_ajax2() {
	$.ajax({
        type: "GET",
        url: "/loginForm",
        processData: false,
        contentType: false,
        cache: false,
        timeout: 600000,
        success: function (data) {
        	$("#app").empty();
        	$("#app").append(data);
        },
    error: function (e) {
        console.log("Error: ", e)
    }
    });
}


function handleClickOrders(event) {
  event.preventDefault();
  fire_ajax3();
}

function  fire_ajax3() {
	$.ajax({
        type: "GET",
        url: "/admin/orders",
        processData: false,
        contentType: false,
        cache: false,
        timeout: 600000,
        success: function (data) {
        	$("#app").empty();
        	$("#app").append(data);
        },
    error: function (e) {
        console.log("Error: ", e)
    }
    });
}


function dropDown(event) {
		    var val = $("#search").val();
		    if(val.length > 1){
		    	$.ajax({
		            type: "GET",
		            url: "/search?value="+val,
		            processData: false,// prevent jQuery from automatically
		            // transforming the data into a query string
		            contentType: false,
		            cache: false,
		            timeout: 6000,
		            success: function (data) {
		            	$('.dropdown-toggle').dropdown();
		            	$("#dropdown-container").empty();
		            	$("#dropdown-container").append(data);
		            },
		    	  error: function (e) {
		              console.log("ERROR : ", e);
		          }
		        });
		    }
}



