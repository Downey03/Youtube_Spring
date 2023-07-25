package com.youtube.Youtube.Service;

import com.youtube.Youtube.DTO.PlayListDTO;

public interface VideoService {
    PlayListDTO videoSearch(String searchKeyword);
    PlayListDTO videoSearch();
}
