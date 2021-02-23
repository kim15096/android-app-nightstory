package app.me.nightstory.lobby;

import com.google.firebase.Timestamp;

import java.util.Date;

public class LobbyPostModel {

    public String category, title, lobbyID, hostName, hostID;
    public Date timestamp;
    public Long cur_views, tot_views;

    public LobbyPostModel() {
    }



    public LobbyPostModel(String category, String title, String lobbyID, String hostID, String hostName, Timestamp timestamp, Long cur_views, Long tot_views) {
        this.category = category;
        this.timestamp = timestamp.toDate();
        this.title = title;
        this.lobbyID = lobbyID;
        this.hostID = hostID;
        this.hostName = hostName;
        this.cur_views = cur_views;
        this.tot_views = tot_views;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLobbyID() {
        return lobbyID;
    }

    public void setLobbyID(String lobbyID) {
        this.lobbyID = lobbyID;
    }

    public String getHostName() {
        return hostName;
    }

    public void setHostName(String hostName) {
        this.hostName = hostName;
    }

    public String getHostID() {
        return hostID;
    }

    public void setHostID(String hostID) {
        this.hostID = hostID;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public Long getCur_views() {
        return cur_views;
    }

    public void setCur_views(Long cur_views) {
        this.cur_views = cur_views;
    }

    public Long getTot_views() {
        return tot_views;
    }

    public void setTot_views(Long tot_views) {
        this.tot_views = tot_views;
    }
}


