let createPlayListInput = document.getElementById("create-playlist-input-field")
let createPlayListBtn = document.getElementById("create-playlist-btn")

let homeVideoSearchInput = document.getElementById("home-video-search-input")
let homeSearchBtn = document.getElementById("home-search-btn")

let playListVideoSearchInput = document.getElementById("playlist-video-search-input")
let searchResultsInPlayList = document.getElementById("search-results-in-playlist")

let playListModal = document.getElementById("playlist-modal")
let logout = document.getElementById("logout-btn")


let deletePlayListBtn = document.getElementById("delete-playlist-btn")

let playLists = [];
let currentPlayListName = "";
let currentPlayList= [];
let playListSearchResults = [];


function changeCurrentPlayList(playListName){
    currentPlayListName = playListName
}

function goHome(){
    window.location.href = `${url}`
}

createPlayListInput.addEventListener('keyup',checkCreatePlayList)
createPlayListBtn.addEventListener('click',function(){
    createPlayListInput.value = ""
})



function showPlayLists(){
    let playLists = document.getElementById("select-playlist")
    if(window.getComputedStyle(playLists).display == "block"){
        playLists.style.display = "none"
    }else{
        playLists.style.display = "block"
    }
    
    
}

async function checkAndAddItemToPlayList(videoTitle){

    console.log(videoTitle,currentPlayList)
    if(currentPlayList.length == 0 ) {
        requests.addItemToPlayList(videoTitle)
    }else{
        for(x in currentPlayList){
            console.log(currentPlayList[x].videoTitle.toString(), videoTitle.toString())
            if(currentPlayList[x].videoTitle == videoTitle){
                console.log("saf")
                animateAlreadyFound()
                return;
            }
         }
         requests.addItemToPlayList(videoTitle)
         return new Promise(resolve => resolve())
    
    }
    // if(currentPlayList[].includes(videoTitle)){
    //     console.log("saf")
    //     animateAlreadyFound()
    // }else{
    //     requests.addItemToPlayList(videoTitle)
    // }
}

function animateAlreadyFound(){
    let element = document.getElementById("already-found-item")
    element.setAttribute("class","animate")
    // element.classList.add("animate")
    setTimeout(removeAnimate,4000,element)
}

function removeAnimate(element){
    
    element.classList.remove("animate")   
}

// function clearPlayListSearchInput(){
//     playListVideoSearchInput.value = ""
// }


function removeItem(videoTitle){
    for(x in currentPlayList){
        if(currentPlayList[x].videoTitle == videoTitle){
            console.log("removing ",currentPlayList[x].videoTitle)
            currentPlayList.splice(x,1)
        }
    }
    return currentPlayList
}

function addItem(videoTitle){
    for(x in playListSearchResults){
        if(playListSearchResults[x].videoTitle == videoTitle){
            currentPlayList.push(playListSearchResults[x])
        }
    }
    return currentPlayList
}

function checkCreatePlayList(){
    if(createPlayListInput.value.length >= 3 && !containsSamePlayList()) createPlayListBtn.disabled = false
    else createPlayListBtn.disabled = true
}

function containsSamePlayList(){
    if(playLists.includes(createPlayListInput.value.toString().trim())) return true;
    else return false
}

// playListModal.addEventListener('hide.bs.modal',clearPlayListSearchInput)