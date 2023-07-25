(function() {
	window.onpageshow = function(event) {
		if (event.persisted) {
			window.location.reload();
		}
    console.log("reloaded")
	};
})();

const laurl = "http://localhost:8091/";
const tomcaturl = "http://localhost:8080/Youtube_Mimic_JS/";
const url1 = "https://sound-groove-380715.de.r.appspot.com/";
//const url = "https://youtubev1-dot-sound-groove-380715.de.r.appspot.com/";
const url2 = "https://karthick-dot-sound-groove-380715.de.r.appspot.com/";
const url = "http://localhost:8080/"

let jwtToken = localStorage.getItem("jwtToken")

// if(jwtToken == null){
//     let loc = window.location.pathname;
//     let arr = loc.split('/')
//     window.location.href = url+"page.html"
// //    window.location.href = window.location.origin+"/"+arr[1]+`/page.html`
// }



