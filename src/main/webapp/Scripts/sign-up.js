

let signupName = document.getElementById("name")
let signupEmail = document.getElementById("email")
let password = document.getElementById("password")
let confPassword = document.getElementById("confirm-password")
let passWarn = document.getElementById("pass-check")
let confPass = document.getElementById("conf-pass")
let signupSubmit = document.getElementById("signup-submit")
let passwordToggle = document.getElementById("password-toggle")
const laurl = "http://localhost:8091/";
const tomcaturl = "http://localhost:8080/Youtube_Mimic_JS/";
const url1 = "https://sound-groove-380715.de.r.appspot.com/";
//const url = "https://youtubev1-dot-sound-groove-380715.de.r.appspot.com/";
const url = "http://localhost:8080/"


passwordToggle.addEventListener('onclick',passwordToggle)
password.addEventListener('keyup',passwordValidation)
confPassword.addEventListener('keyup',passwordValidation)
password.addEventListener('keyup',validateSignup)
confPassword.addEventListener('keyup',validateSignup)
signupEmail.addEventListener('keyup',validateSignup)
signupName.addEventListener('keyup',validateSignup)



function validateSignup(){
    let passWarn = document.getElementById("pass-check")
    let confPass = document.getElementById("conf-pass")

    if(password.value != "" && passWarn.outerText == "" && confPass.outerText == "" && nameValidation()){
        signupSubmit.disabled = false
    }else{
        signupSubmit.disabled = true
    }
}

function passwordValidation(){
    if(password.value.length < 8) passWarn.innerText =  "Password size must be more than 8"
    else if(!password.value.match(/[a-zA-z]/g)) passWarn.innerText = "Password must have a letter"
    else if(!password.value.match(/[0-9]/g)) passWarn.innerText = "Password must have a number"
    else passWarn.innerText = ""
    if(password.value.toString().length == 0) passWarn.innerText = "";
    if(confPassword.value.toString().length == 0) confPass.innerText = "";
    if(password.value != confPassword.value){
        confPass.innerText = "Password and Confirm Password must be same";
    }else {
        confPass.innerHTML = ""
    }
}

function nameValidation(){
    if(signupName.value.length >= 3 && signupEmail.value.length > 10 && signupEmail.value.toString().includes('@') && signupEmail.value.toString().includes(".")){
        return true
    }else{
        return false
    }
}

function togglePassword(){
    let showPass = document.getElementById("password-toggle")
    let password = document.getElementById("password")
    if(password.type=="password"){
        password.type="text"
        showPass.innerText = "hide"
    }
    else{
        password.type="password"
        showPass.innerText = "show"
    }
}


let signup = async function(){
    let userName = document.getElementById("name").value;
    let userEmail = document.getElementById("email").value;
    let password = document.getElementById("password").value;

    let response =await fetch(`${url}user/create`, {
        method: 'POST',
        headers: {
            "Content-Type":"application/json",
            "Accept":"/*"
        },
        body:JSON.stringify({
            userName : `${userName}`,
            userEmail : `${userEmail}`,
            password : `${password}`
        }),
        redirect:"follow"
    })

    if(response.status == 406){
        let data = await response.json()
        exception(data)
    }else{
        let data = await response.json();
        localStorage.setItem("jwtToken",data);
//        window.location.href=url+"home.html"
         window.location.reload()
    }
}


 signupSubmit.addEventListener('click',signup)

 let homeBtn = document.getElementById("go-home-btn")

function goHome(){
    window.location.href = `${url}page.html`
 }

 function exception(data){
    let element = document.getElementById("signup-exception")
    element.innerText = data
    console.log(data)
    element.setAttribute("class","animate")
    element.classList.add("animate")
    setTimeout(removeAnimateSignup,4000,element)
 }

 function removeAnimateSignup(){
    document.getElementById("signup-exception").classList.remove("animate")
}