package com.topnotch.developers.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class GithubUserProfile implements Parcelable {
    @SerializedName("login")
    public String login;
    @SerializedName("id")
    public Integer id;
    @SerializedName("node_id")
    public String nodeId;
    @SerializedName("avatar_url")
    public String avatarUrl;
    @SerializedName("gravatar_id")
    public String gravatarId;
    @SerializedName("url")
    public String url;
    @SerializedName("html_url")
    public String htmlUrl;
    @SerializedName("followers_url")
    public String followersUrl;
    @SerializedName("following_url")
    public String followingUrl;
    @SerializedName("gists_url")
    public String gistsUrl;
    @SerializedName("starred_url")
    public String starredUrl;
    @SerializedName("subscriptions_url")
    public String subscriptionsUrl;
    @SerializedName("organizations_url")
    public String organizationsUrl;
    @SerializedName("repos_url")
    public String reposUrl;
    @SerializedName("events_url")
    public String eventsUrl;
    @SerializedName("received_events_url")
    public String receivedEventsUrl;
    @SerializedName("type")
    public String type;
    @SerializedName("site_admin")
    public Boolean siteAdmin;
    @SerializedName("name")
    public String name;
    @SerializedName("company")
    public String company;
    @SerializedName("blog")
    public String blog;
    @SerializedName("location")
    public String location;
    @SerializedName("email")
    public String email;
    @SerializedName("hireable")
    public Boolean hireable;
    @SerializedName("bio")
    public String bio;
    @SerializedName("twitter_username")
    public String twitterUsername;
    @SerializedName("public_repos")
    public Integer publicRepos;
    @SerializedName("public_gists")
    public Integer publicGists;
    @SerializedName("followers")
    public Integer followers;
    @SerializedName("following")
    public Integer following;
    @SerializedName("created_at")
    public String createdAt;
    @SerializedName("updated_at")
    public String updatedAt;



    public String getLogin() {
        return login;
    }

    public int getId() {
        return id;
    }

    public String getNodeId() {
        return nodeId;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public String getGravatarId() {
        return gravatarId;
    }

    public String getUrl() {
        return url;
    }

    public String getHtmlUrl() {
        return htmlUrl;
    }

    public String getFollowersUrl() {
        return followersUrl;
    }

    public String getFollowingUrl() {
        return followingUrl;
    }

    public String getGistsUrl() {
        return gistsUrl;
    }

    public String getStarredUrl() {
        return starredUrl;
    }

    public String getSubscriptionsUrl() {
        return subscriptionsUrl;
    }

    public String getOrganizationsUrl() {
        return organizationsUrl;
    }

    public String getReposUrl() {
        return reposUrl;
    }

    public String getEventsUrl() {
        return eventsUrl;
    }

    public String getReceivedEventsUrl() {
        return receivedEventsUrl;
    }

    public String getType() {
        return type;
    }

    public Boolean getSiteAdmin() {
        return siteAdmin;
    }

    public String getName() {
        return name;
    }

    public String getCompany() {
        return company;
    }

    public String getBlog() {
        return blog;
    }

    public String getLocation() {
        return location;
    }

    public String getEmail() {
        return email;
    }

    public Boolean getHireable() {
        return hireable;
    }

    public String getBio() {
        return bio;
    }

    public String getTwitterUsername() {
        return twitterUsername;
    }

    public int getPublicRepos() {
        return publicRepos;
    }

    public int getPublicGists() {
        return publicGists;
    }

    public int getFollowers() {
        return followers;
    }

    public int getFollowing() {
        return following;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.login);
        dest.writeInt(this.id);
        dest.writeString(this.nodeId);
        dest.writeString(this.avatarUrl);
        dest.writeString(this.gravatarId);
        dest.writeString(this.url);
        dest.writeString(this.htmlUrl);
        dest.writeString(this.followersUrl);
        dest.writeString(this.followingUrl);
        dest.writeString(this.gistsUrl);
        dest.writeString(this.starredUrl);
        dest.writeString(this.subscriptionsUrl);
        dest.writeString(this.organizationsUrl);
        dest.writeString(this.reposUrl);
        dest.writeString(this.eventsUrl);
        dest.writeString(this.receivedEventsUrl);
        dest.writeString(this.type);
        dest.writeValue(this.siteAdmin);
        dest.writeString(this.name);
        dest.writeString(this.company);
        dest.writeString(this.blog);
        dest.writeString(this.location);
        dest.writeString(this.email);
        dest.writeValue(this.hireable);
        dest.writeString(this.bio);
        dest.writeString(this.twitterUsername);
        dest.writeInt(this.publicRepos);
        dest.writeInt(this.publicGists);
        dest.writeInt(this.followers);
        dest.writeInt(this.following);
        dest.writeString(this.createdAt);
        dest.writeString(this.updatedAt);
    }

    protected GithubUserProfile(Parcel in) {
        login = in.readString();
        id = in.readInt();
        nodeId = in.readString();
        avatarUrl = in.readString();
        gravatarId = in.readString();
        url = in.readString();
        htmlUrl = in.readString();
        followersUrl = in.readString();
        followingUrl = in.readString();
        gistsUrl = in.readString();
        starredUrl = in.readString();
        subscriptionsUrl = in.readString();
        organizationsUrl = in.readString();
        reposUrl = in.readString();
        eventsUrl = in.readString();
        receivedEventsUrl = in.readString();
        type = in.readString();
        siteAdmin = (Boolean)in.readValue(null);
        name = in.readString();
        company = in.readString();
        blog = in.readString();
        location = in.readString();
        email = in.readString();
        hireable = (Boolean)in.readValue(null);
        bio = in.readString();
        twitterUsername = in.readString();
        publicRepos = in.readInt();
        publicGists = in.readInt();
        followers = in.readInt();
        following = in.readInt();
        createdAt = in.readString();
        updatedAt = in.readString();
    }

    public static final Creator<GithubUserProfile> CREATOR = new Creator<GithubUserProfile>() {
        @Override
        public GithubUserProfile createFromParcel(Parcel in) {
            return new GithubUserProfile(in);
        }

        @Override
        public GithubUserProfile[] newArray(int size) {
            return new GithubUserProfile[size];
        }
    };
}
