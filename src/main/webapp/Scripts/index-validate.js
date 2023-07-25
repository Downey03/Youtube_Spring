

(function(){
	window.onpageshow = function(event) {
		if (event.persisted) {
			window.location.reload();
		}
	}
})();

const url = "http://localhost:8080/"
// function sessionValidate() {
	
// 	let respose = fetch(`${url}session_validate`,{
// 		method: "POST",
// 		body : JSON.stringify({
// 			jwtToken : localStorage.getItem("jwtToken")
// 		})
// 	}).then(res => {if(res.status==200){
// 		window.location.href = url+"home.html"
// 	}})

// 	// window.onpageshow = function(event) {
// 	// 	if (event.persisted) {
// 	// 		window.location.reload();
// 	// 	}
//     // console.log("reloaded")
// 	// };
// };


// sessionValidate()

// window.onpageshow = sessionValidate()
//const url = "https://youtubev1-dot-sound-groove-380715.de.r.appspot.com/";


// if(localStorage.getItem("jwtToken")!=null){
//     window.location.href = url+"home.html"
// }