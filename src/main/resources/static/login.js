$('#submit-btn').ready(function () {
	    $('button').click(function (e) {
//	        e.preventDefault();
	        //console.log("button cliceks");
//	        login();
	    });
	});
	
//	$(document).ready(function () {
////		generatecsrf();
//	});
	

	
	function login() {
	    username = $('#userName').val();
	    password = $('#userPassword').val();
	
	    if (username != "" && password != "") {
	        $.ajax({
	            type: "post",
	            url: "/",
	            data: '{"username":' + username + ',"password":' + password + '}',
	            contentType: "application/json; charset=utf-8",
	            dataType: "json",
	            success: function (output) {
	                if (output.value == "Authorized") {
	                    $('.info').html('');
	                    location.reload();
	                } else if (output.value == "Invalid") {
	                    alert("Invalid session. Retry..");
	                    location.reload();
	                } else {
	                    $('.info').html('login failed. Invalid credentials');
	                }
	            },
	            error: function () {
	                alert("error");
	            }
	        });
	    } else {
	        $('.info').html('Please fill both fields');
	    }
	}
	
	function generatecsrf() {
	    $.ajax({
	        type: "post",
	        url: "/csrf",
	        contentType: "application/json; charset=utf-8",
	        success:function (data){
	        	console.log(data)
	        },
	        error: function () {
	            alert("error");
	        }
	    });
	}