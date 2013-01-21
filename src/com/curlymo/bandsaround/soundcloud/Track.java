package com.curlymo.bandsaround.soundcloud;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Track implements Serializable{

	String id;
	String created_at;
	String user_id;
	String duration;
	Boolean commentable;
	String state;
	String sharing;
	String tag_list;
	String permalink;
	String description;
	String streamable;
	String downloadable;
	String genre;
	String release;
	String purchase_url;
	String label_id;
	String label_name;
	String isrc;
	String video_url;
	String track_type;
	String key_signature;
	String bpm;
	String title;
	String release_year;
	String release_month;
	String release_day;
	String original_format;
	String original_content_size;
	String license;
	String uri;
	String permalink_url;
	String artwork_url;
	String waveform_url;
	String stream_url;
	String download_url;
	String playback_count;
	String download_count;
	String favoritings_count;
	String comment_count;
	String attachments_uri;
	User user;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCreated_at() {
		return created_at;
	}
	public void setCreated_at(String created_at) {
		this.created_at = created_at;
	}
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	public String getDuration() {
		return duration;
	}
	public void setDuration(String duration) {
		this.duration = duration;
	}
	public Boolean getCommentable() {
		return commentable;
	}
	public void setCommentable(Boolean commentable) {
		this.commentable = commentable;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getSharing() {
		return sharing;
	}
	public void setSharing(String sharing) {
		this.sharing = sharing;
	}
	public String getTag_list() {
		return tag_list;
	}
	public void setTag_list(String tag_list) {
		this.tag_list = tag_list;
	}
	public String getPermalink() {
		return permalink;
	}
	public void setPermalink(String permalink) {
		this.permalink = permalink;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getStreamable() {
		return streamable;
	}
	public void setStreamable(String streamable) {
		this.streamable = streamable;
	}
	public String getDownloadable() {
		return downloadable;
	}
	public void setDownloadable(String downloadable) {
		this.downloadable = downloadable;
	}
	public String getGenre() {
		return genre;
	}
	public void setGenre(String genre) {
		this.genre = genre;
	}
	public String getRelease() {
		return release;
	}
	public void setRelease(String release) {
		this.release = release;
	}
	public String getPurchase_url() {
		return purchase_url;
	}
	public void setPurchase_url(String purchase_url) {
		this.purchase_url = purchase_url;
	}
	public String getLabel_id() {
		return label_id;
	}
	public void setLabel_id(String label_id) {
		this.label_id = label_id;
	}
	public String getLabel_name() {
		return label_name;
	}
	public void setLabel_name(String label_name) {
		this.label_name = label_name;
	}
	public String getIsrc() {
		return isrc;
	}
	public void setIsrc(String isrc) {
		this.isrc = isrc;
	}
	public String getVideo_url() {
		return video_url;
	}
	public void setVideo_url(String video_url) {
		this.video_url = video_url;
	}
	public String getTrack_type() {
		return track_type;
	}
	public void setTrack_type(String track_type) {
		this.track_type = track_type;
	}
	public String getKey_signature() {
		return key_signature;
	}
	public void setKey_signature(String key_signature) {
		this.key_signature = key_signature;
	}
	public String getBpm() {
		return bpm;
	}
	public void setBpm(String bpm) {
		this.bpm = bpm;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getRelease_year() {
		return release_year;
	}
	public void setRelease_year(String release_year) {
		this.release_year = release_year;
	}
	public String getRelease_month() {
		return release_month;
	}
	public void setRelease_month(String release_month) {
		this.release_month = release_month;
	}
	public String getRelease_day() {
		return release_day;
	}
	public void setRelease_day(String release_day) {
		this.release_day = release_day;
	}
	public String getOriginal_format() {
		return original_format;
	}
	public void setOriginal_format(String original_format) {
		this.original_format = original_format;
	}
	public String getOriginal_content_size() {
		return original_content_size;
	}
	public void setOriginal_content_size(String original_content_size) {
		this.original_content_size = original_content_size;
	}
	public String getLicense() {
		return license;
	}
	public void setLicense(String license) {
		this.license = license;
	}
	public String getUri() {
		return uri;
	}
	public void setUri(String uri) {
		this.uri = uri;
	}
	public String getPermalink_url() {
		return permalink_url;
	}
	public void setPermalink_url(String permalink_url) {
		this.permalink_url = permalink_url;
	}
	public String getArtwork_url() {
		return artwork_url;
	}
	public void setArtwork_url(String artwork_url) {
		this.artwork_url = artwork_url;
	}
	public String getWaveform_url() {
		return waveform_url;
	}
	public void setWaveform_url(String waveform_url) {
		this.waveform_url = waveform_url;
	}
	public String getStream_url() {
		return stream_url;
	}
	public void setStream_url(String stream_url) {
		this.stream_url = stream_url;
	}
	public String getDownload_url() {
		return download_url;
	}
	public void setDownload_url(String download_url) {
		this.download_url = download_url;
	}
	public String getPlayback_count() {
		return playback_count;
	}
	public void setPlayback_count(String playback_count) {
		this.playback_count = playback_count;
	}
	public String getDownload_count() {
		return download_count;
	}
	public void setDownload_count(String download_count) {
		this.download_count = download_count;
	}
	public String getFavoritings_count() {
		return favoritings_count;
	}
	public void setFavoritings_count(String favoritings_count) {
		this.favoritings_count = favoritings_count;
	}
	public String getComment_count() {
		return comment_count;
	}
	public void setComment_count(String comment_count) {
		this.comment_count = comment_count;
	}
	public String getAttachments_uri() {
		return attachments_uri;
	}
	public void setAttachments_uri(String attachments_uri) {
		this.attachments_uri = attachments_uri;
	}
	
    public User getUser() {
        return user;
    }
    
    public void setUser(User user) {
    	this.user = user;
    }
}
