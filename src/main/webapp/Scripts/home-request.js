// const url = "http://localhost:8080/Youtube_Mimic_JS/";
// let jwtToken = localStorage.getItem("jwtToken")

homeVideoSearchInput.addEventListener('keyup',function(e){
    if(e.key == "Enter") requests.getHomeVideo()
})



const writeFunctions = {

    writeHomeVideo : function(data,element){

        let writeData = "";
        

        let newEl = document.getElementById("home-results")
        console.log(newEl)
        data.forEach(obj => {
            let aTag = document.createElement("a");
            let imgTag = document.createElement("img")
            let text = document.createElement("p")
            aTag.setAttribute("href",`${obj.videoLink}`)
            imgTag.setAttribute("src",`${obj.videoThumbnail}`)
            text.textContent = `${obj.videoTitle}`;
            aTag.append(imgTag,text)
            newEl.append(aTag)
            
            
        })

        console.log(newEl)
        // data.forEach(obj => {
        //     writeData=writeData+`
        //         <a href="${obj.videoLink}">
        //             <img src="${obj.videoThumbnail}">
        //             ${obj.videoTitle}
        //         </a>`})

//        for(x in data){
//            writeData = writeData+`
//                <a href="${data[x].videoLink}">
//                    <img src="${data[x].videoThumbnail}">
//                    ${data[x].videoTitle}
//                </a>`;
//        }

        element.innerHTML = writeData;
    },
    writeHomeVideoNotFound : function(element){
        let writeData = "<h3>No Video Found</h3>";
        element.innerHTML = writeData
    },
    writePlayList : function(){
        
        let writeDocument = document.getElementById("select-playlist")
        let writeData = "";
        
        for(x in playLists) {
            writeData = writeData+`<div><button  class="playlists"  onclick="switchPlayList('${playLists[x]}')">${playLists[x]}</button> <i data-bs-toggle="modal" data-bs-target="#confirm-playlist-delete" onclick="changeCurrentPlayList('${playLists[x]}')" class="fa fa-circle-xmark delete-video"></i></div>`
            // writeData = writeData+`<div><a  href="this.href+playlist.html"  onclick="location.href=this.href+'?playListName=${playLists[x]}'">${playLists[x]}</a> <i data-bs-toggle="modal" data-bs-target="#confirm-playlist-delete" onclick="changeCurrentPlayList('${playLists[x]}')" class="fa fa-circle-xmark delete-video"></i></div>`
            // writeData = writeData+`<button><div><a href="${url}playlist.html">${playLists[x]}</a></p><i data-bs-toggle="modal" data-bs-target="#confirm-playlist-delete" onclick="changeCurrentPlayList('${playLists[x]}')" class="fa fa-circle-xmark delete-video"></i></div></button>`
        }
        writeDocument.innerHTML = writeData
    },
    writePlayListContent : function(playListName,data,element){

        
        let writeData = `<input type="hidden" id=playListName value="${playListName}">`
        // currentPlayList = [];
        for(x in data){
            // currentPlayList.push(`${data[x].videoTitle}`)
            writeData = writeData+`<div>
                <a href="${data[x].videoLink}">
                    <div>
                        <img src="${data[x].videoThumbnail}">
                        <h6>${data[x].videoTitle}</h6>
                    </div>
                </a>
                <i onclick="requests.removeItemFromPlayList('${data[x].videoTitle}')" class="fa fa-circle-xmar
                k delete-video"></i>
            </div>`
        }

        element.innerHTML = writeData;
    },
    writePlayListVideo : function(data){

        let writeDocument = document.getElementById("search-results-in-playlist")

        let writeData = '<h6 id="already-found-item">Video Already Found In PlayList</h6>';

        ///need some cahnges
        for(x in data){
            writeData = writeData+`<div><button  onclick='clearPlayListSearchInput();checkAndAddItemToPlayList(\"${data[x].videoTitle}\")'  value="" >${data[x].videoTitle}</button></div>`
            // writeData = writeData+"<div><button onclick=checkAndAddItemToPlayList(\'"+`${data[x].videoTitle}`+"\')  >"+`${data[x].videoTitle}`+"</button></div>"

        }

        writeDocument.innerHTML = writeData
        
    }


}

function switchPlayList(playListName){

    let loc = window.location.pathname;
    let arr = loc.split('/')

   window.location.href = window.location.origin+`/playlist?playListName=${playListName}`
//    window.location.href = window.location.origin+"/"+arr[1]+`/playlist.html?playListName=${playListName}`
}

const requests = {

    getHomeVideo : async function(){

        let element = document.getElementById("home-results")
        element.innerHTML = "";
        let searchKeyword = homeVideoSearchInput.value
        let response = await fetch(`${url}video/search?searchKeyword=${searchKeyword}`,{
            headers : {
                "Content-Type" : "application/json",
                "Authorization" : `bearer ${jwtToken}`,
                "Accept":"application/json"
            },
            method : "Get",
            
            // body : JSON.stringify({
            //     "searchKeyword" : `${searchKeyword}`
            // })
        })

        
        if(response.status == 404){
            writeFunctions.writeHomeVideoNotFound(element)
        }else{
            let data = await response.json();
            console.log(data.model)
            let start = performance.now()
            writeFunctions.writeHomeVideo(data.modelMap.YTLinkList,element)
            let timetook = performance.now()-start
            console.log(timetook,"ms")
        }
    },

    createPlayList : async function(){

        let playListName = createPlayListInput.value

        playLists.push(playListName)

        writeFunctions.writePlayList()

        let response = await fetch(`${url}playlist/create`,{
            headers: {
                contentType: 'application/json',
                Authorization: `bearer ${(localStorage.getItem("jwtToken"))}`,
                Accept : "/*"
            },
            method:"POST",
            body : JSON.stringify({
                playListName : `${playListName}`
            })
        })

        let data = await response.json();
        playLists = data.model.playLists;
    },
    getPlayLists : async function(){


        let response = await fetch(`${url}playlist/get`,{
            headers: { "Content-Type": "application/json",
            "Authorization" : `Bearer ${jwtToken}`,
            "Accept":"application/json"
                },
            method : "GET",
            
            }).then(res => res.json())
        
            console.log(response)
            // if(response.status == 401){
            //     window.location.href = url+"page.html"
            // }
            
        // playLists =await response.json()
        playLists = response.modelMap.playLists;
        writeFunctions.writePlayList()
        
    },

    viewPlayList : async function(playListName){

        currentPlayListName = playListName;

        let element = document.getElementById("playlist-items")
        currentPlayList = [];
        writeFunctions.writePlayListContent(playListName,currentPlayList,element);
        let response = await fetch(`${url}playlist/view?playListName=${playListName}`,{
            headers: { "Content-Type": "application/json",
            "Authorization" : `Bearer ${jwtToken}`,
            "Accept":"/*"
                },
            // body : JSON.stringify({
            //     playListName : `${playListName}`
            // }),
            method : "GET",
            Accept:"/*",
        }).then(res => res.json())

        currentPlayList = response
        writeFunctions.writePlayListContent(playListName,response,element);
        
    },
    
    deletePlayList :async function(){

        let playListName = currentPlayListName
        
        console.log(playLists)
        let idx = playLists.indexOf(playListName)
        playLists.splice(idx,1)
        console.log(playLists)
        writeFunctions.writePlayList()

        let response = await fetch(`${url}playlist/delete`,{
            headers: { "Content-Type": "application/json",
            "Authorization" : `Bearer ${jwtToken}`,
            "Accept":"/*"
                },
            body: JSON.stringify({
                playListName 
            }),
            method : "POST",
            Accept:"/*",
        }).then(res => res.json())

        // playLists = response

        // writeFunctions.writePlayList()
        
    },

    getPlayListVideo : async function(){

        let searchKeyword = document.getElementById("playlist-video-search-input").value
        let response = await fetch(`${url}video/search`,{
            headers: { "Content-Type": "application/json",
            "Authorization" : `Bearer ${jwtToken}`,
            "Accept":"/*"
                },
            body:JSON.stringify({
                searchKeyword : `${searchKeyword}`
            }),
            method : "POST",
            Accept:"/*",
        }).then(res => res.json())

        playListSearchResults = response

        writeFunctions.writePlayListVideo(response)
    },
    addItemToPlayList : async function(videoTitle){

        let playListName = document.getElementById("playListName").value
        let element = document.getElementById("playlist-items")

        currentPlayList = addItem(videoTitle);

        writeFunctions.writePlayListContent(playListName,currentPlayList,element)
        await fetch(`${url}playlist/add`,{
            headers : {
                "Content-Type" : "application/json",
                "Authorization" : `Bearer ${jwtToken}`,
                "Accept":"/*"
            },
            method : "POST",
            Accept : "/*",
            body : JSON.stringify({
                "playListName" : `${playListName}`,
                "videoTitle" : `${videoTitle}`
            })
        }).then(res => res.json())

        // currentPlayList = response   
        
    },
    removeItemFromPlayList : async function(videoTitle){

        let playListName = document.getElementById("playListName").value
        let element = document.getElementById("playlist-items")

        currentPlayList = removeItem(videoTitle)
        writeFunctions.writePlayListContent(playListName,currentPlayList,element)
        let response = await fetch(`${url}playlist/remove`,{
            headers: { 
                "Content-Type": "application/json",
                "Authorization" : `Bearer ${jwtToken}`,
                "Accept":"/*"
                },
            method : "POST",
            Accept:"/*",
            body: JSON.stringify({
                "playListName": `${playListName}`,
                "videoTitle" : `${videoTitle}`
              })   
        }).then(res => res.json())
    },

    logout : function(){
        localStorage.clear();
        let x = window.location.pathname;
        let arr = x.split("/")
        fetch(`${url}user/logout`,{
            method: "GET"
        })
        window.location.reload()

        // window.location.reload()
//        window.location.href = window.location.origin+"/"+arr[1]+`/page.html`
        // window.location.href=url+"page.html"
    }
    
}

requests.getHomeVideo()
requests.getPlayLists()

