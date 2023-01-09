function handleClickAddProduct(event)
{
  event.preventDefault();
  fire_ajax();
}
function  fire_ajax()
{
	$.ajax({
        type: "GET",
        url: "/Admin/products",
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

function handleClickProductList(event)
{
  event.preventDefault();
  fire_ajax1();
}
function  fire_ajax1()
{
	$.ajax({
        type: "GET",
        url: "/Admin/productList",
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

function handleClickLogin(event)
{
  event.preventDefault();
  fire_ajax2();
}
function  fire_ajax2()
{
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



