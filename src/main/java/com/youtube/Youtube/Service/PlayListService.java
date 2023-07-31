package com.youtube.Youtube.Service;

import com.youtube.Youtube.DTO.PlayListDTO;

public interface PlayListService {

    PlayListDTO createPlayList(PlayListDTO playListDTO) throws Exception;

    PlayListDTO getPlayLists(PlayListDTO playListDTO);

    PlayListDTO getPlayList(PlayListDTO playListDTO);

    PlayListDTO deletePlayList(PlayListDTO playListDTO);

    PlayListDTO addItemToPlayList(PlayListDTO playListDTO);

    PlayListDTO removeItemFromPlayList(PlayListDTO playListDTO);

    void updatePlayListName(PlayListDTO playListDTO);
}
