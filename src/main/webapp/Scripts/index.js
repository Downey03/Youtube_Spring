let indexVideoSearchInput = document.getElementById("index-video-search-input")
let indexVideoSearchBtn = document.getElementById("index-search-btn")
let loginEmail = document.getElementById("login-email")
let loginPass = document.getElementById("login-pass")
let loginSubmit = document.getElementById("login-submit")
let signupName = document.getElementById("name")
let signupEmail = document.getElementById("email")
let password = document.getElementById("password")
let confPassword = document.getElementById("confirm-password")
let passWarn = document.getElementById("pass-check")
let confPass = document.getElementById("conf-pass")
let signupSubmit = document.getElementById("signup-submit")
let passwordToggle = document.getElementById("password-toggle")
const urllo = "http://localhost:8091/";
const tomcaturl = "http://localhost:8080/Youtube_Mimic_JS/";
const url1 = "https://sound-groove-380715.de.r.appspot.com/";
//const url = "https://youtubev1-dot-sound-groove-380715.de.r.appspot.com/";
//const url = "http://localhost:8091/"

// signupSubmit.addEventListener('onclick',requests.login)






passwordToggle.addEventListener('onclick',passwordToggle)
password.addEventListener('keyup',passwordValidation)
confPassword.addEventListener('keyup',passwordValidation)
password.addEventListener('keyup',validateSignup)
confPassword.addEventListener('keyup',validateSignup)
signupEmail.addEventListener('keyup',validateSignup)
signupName.addEventListener('keyup',validateSignup)
loginEmail.addEventListener('keyup',loginValidate)
loginPass.addEventListener('keyup',loginValidate)
indexVideoSearchInput.addEventListener('keypress',function(e){
    if(e.key == "Enter"){
        requests.getIndexVideo()
    }
})

let writeFunctions = {

    writeIndexVideo : function(data,element){

        console.log(data,element)
        let writeData = "";
        for(x in data){
            writeData = writeData+`
                <a href="${data[x].videoLink}">
                    <img src="${data[x].videoThumbnail}">
                    ${data[x].videoTitle}
                </a>`;
        }

        if(writeData != "")
        element.innerHTML = writeData
    },
    writeIndexVideoNotFound : function(element){

        let writeData  = "<h3>No Video Found</h3>";
        element.innerHTML = writeData
    },
    writeLoginException : function(data){
        let element = document.getElementById("login-exception")
        element.innerText = "Invalid Login Credentials"
        
        element.setAttribute("class","animate")
        setTimeout(removeAnimateLogin,4000,element)
    },
    writeSignUpException : function(data){
        let element = document.getElementById("signup-exception")
        element.innerText = data
        console.log(data)
        console.log(element)
        element.setAttribute("class","animate")
        element.classList.add("animate")
        setTimeout(removeAnimateSignup,4000,element)
    }

}

let requests = {

    getIndexVideo : async function(){

        let element = document.getElementById("index-results")
        element.innerText = "";
        let searchKeyword = indexVideoSearchInput.value
        let response = await fetch(`${url}video/search?searchKeyword=${searchKeyword}`,{
            headers : {
                "Content-Type" : "application/json"
            },
            method : "GET",
            Accept : "/*",
            // body : JSON.stringify({
            //     searchKeyword : `${searchKeyword}`
            // })
        })

        if(response.status == 200){
            window.location.href = url+"home.html"
        }else if(response.status == 404){
            writeFunctions.writeIndexVideoNotFound(element)
        }else{
            let data = await response.json()
            let start = performance.now()
            writeFunctions.writeIndexVideo(data.modelMap.YTLinkList,element)
            let timetook = performance.now()-start
            console.log(timetook,"ms");
        }
    },
    login : async function(){

        let userEmail = document.getElementById("login-email").value
        let password = document.getElementById("login-pass").value;
        
        let response =await fetch(`${url}user/login`, {
            method: 'PUT',
            headers: {
                "Content-Type":"application/json",
                "Accept" :"application/json"
            },
            body:JSON.stringify({
                userEmail : `${userEmail}`,
                password : `${password}`
            }),
            redirect:"follow"
        })
    
        
        
        if(response.status >= 200 && response.status <=299){
            
            window.location.reload()
        

            // let data = await response.json();
            // console.log(data)
            // localStorage.setItem("jwtToken",data.modelMap.jwtToken);
            // window.location.href= url+data.viewName
            // window.location.href=url+"home"
            // let x = window.location.pathname;
            // let arr = x.split("/")
            // window.location.href = window.location.origin+"/home.html";
//            window.location.href = window.location.origin+"/"+arr[1]+`/home.html`
        }else{
            let data = await response.text();
            console.log(data)
            writeFunctions.writeLoginException(data)
        }
    },
    signup : async function(){
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
            writeFunctions.writeSignUpException(data)
        }else{
            let data = await response.json();
            let x = window.location.pathname;
            let arr = x.split("/")
            localStorage.setItem("jwtToken",data);
            window.location.href = url+"home.html"
//            window.location.href = window.location.origin+"/"+arr[1]+`/home.html`
            // window.location.href=url+"home.html"
        }
    }

}

function loginValidate(){
 
    if(loginEmail.value.length>10 && loginPass.value.length>=8){
        loginSubmit.disabled = false
    }else{
        loginSubmit.disabled = true
    }
}

function removeAnimateSignup(){
    document.getElementById("signup-exception").classList.remove("animate")
}

function removeAnimateLogin(){
    
    document.getElementById("login-exception").classList.remove("animate")
}


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

signupSubmit.addEventListener('click',requests.signup)
loginSubmit.addEventListener('click',requests.login)
indexVideoSearchBtn.addEventListener('click',requests.getIndexVideo)

requests.getIndexVideo()

let loginModal = document.getElementById("login-modal")
let signupModal = document.getElementById("signup-modal")

loginModal.addEventListener('hide.bs.modal',clearInputs)
signupModal.addEventListener('hide.bs.modal',clearInputs)

function clearInputs(){
    loginPass.value = ""
    loginEmail.value = ""
    signupEmail.value = ""
    signupName.value = ""
    password.value = ""
    confPassword.value = ""
    passWarn.inn = ""
    confPass.innerText = ""

}

let signUpPage = document.getElementById("signup-modal-trigger-btn")

signUpPage.addEventListener('click',function(){
    window.location.href = `${url}sign_up`
})



