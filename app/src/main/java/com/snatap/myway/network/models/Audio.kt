package com.snatap.myway.network.models

data class AudioPlaylistsResp(
    val audio_playlists: List<AudioPlaylist>,
    val success: Boolean
)

data class AudioPlaylistResp(
    val audio_playlist: AudioPlaylist,
    val success: Boolean
)

data class AudioPlaylist(
    val audio_items_count: Int,
    val created_at: String,
    val id: Int,
    val is_bookmarked: Boolean,
    val is_recommended: Boolean,
    val photo: String,
    val subtitle: String,
    val title: String,
    val updated_at: String,
    val audio_items: List<Audio>?
)

data class Audio(
    val audio: String,
    val audio_playlist_id: Int,
    val created_at: String,
    val duration: Int,
    val id: Int,
    val order: Int,
    val photo: String,
    val title: String,
    val updated_at: String
)