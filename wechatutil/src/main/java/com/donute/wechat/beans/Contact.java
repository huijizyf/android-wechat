package com.donute.wechat.beans;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by zhouyufei on 2017/9/6.
 */

public class Contact implements Parcelable{
    private int Uin;
    private String        UserName;
    private String        NickName;
    private String       HeadImgUrl;
    private int       ContactFlag;
    private int       MemberCount;
    private List<GroupMember> MemberList;
    private String        RemarkName;
    private int        HideInputBarFlag;
    private int        Sex;
    private String        Signature;
    private int        VerifyFlag;
    private int        OwnerUin;
    private String       PYInitial;
    private String        PYQuanPin;
    private String       RemarkPYInitial;
    private String      RemarkPYQuanPin;
    private int        StarFriend;
    private int        AppAccountFlag;
    private int       Statues;
    private long       AttrStatus;
    private String        Province;
    private String       City;
    private String        Alias;
    private int        SnsFlag;
    private int        UniFriend;
    private String        DisplayName;
    private int ChatRoomId;
    private String        KeyWord;
    private String       EncryChatRoomId;
    private String        IsOwner;

    protected Contact(Parcel in) {
        Uin = in.readInt();
        UserName = in.readString();
        NickName = in.readString();
        HeadImgUrl = in.readString();
        ContactFlag = in.readInt();
        MemberCount = in.readInt();
        RemarkName = in.readString();
        HideInputBarFlag = in.readInt();
        Sex = in.readInt();
        Signature = in.readString();
        VerifyFlag = in.readInt();
        OwnerUin = in.readInt();
        PYInitial = in.readString();
        PYQuanPin = in.readString();
        RemarkPYInitial = in.readString();
        RemarkPYQuanPin = in.readString();
        StarFriend = in.readInt();
        AppAccountFlag = in.readInt();
        Statues = in.readInt();
        AttrStatus = in.readLong();
        Province = in.readString();
        City = in.readString();
        Alias = in.readString();
        SnsFlag = in.readInt();
        UniFriend = in.readInt();
        DisplayName = in.readString();
        ChatRoomId = in.readInt();
        KeyWord = in.readString();
        EncryChatRoomId = in.readString();
        IsOwner = in.readString();
    }

    public static final Creator<Contact> CREATOR = new Creator<Contact>() {
        @Override
        public Contact createFromParcel(Parcel in) {
            return new Contact(in);
        }

        @Override
        public Contact[] newArray(int size) {
            return new Contact[size];
        }
    };

    public int getUin() {
        return Uin;
    }

    public void setUin(int uin) {
        Uin = uin;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getNickName() {
        return NickName;
    }

    public void setNickName(String nickName) {
        NickName = nickName;
    }

    public String getHeadImgUrl() {
        return HeadImgUrl;
    }

    public void setHeadImgUrl(String headImgUrl) {
        HeadImgUrl = headImgUrl;
    }

    public int getContactFlag() {
        return ContactFlag;
    }

    public void setContactFlag(int contactFlag) {
        ContactFlag = contactFlag;
    }

    public int getMemberCount() {
        return MemberCount;
    }

    public void setMemberCount(int memberCount) {
        MemberCount = memberCount;
    }

    public List<GroupMember> getMemberList() {
        return MemberList;
    }

    public void setMemberList(List<GroupMember> memberList) {
        MemberList = memberList;
    }

    public String getRemarkName() {
        return RemarkName;
    }

    public void setRemarkName(String remarkName) {
        RemarkName = remarkName;
    }

    public int getHideInputBarFlag() {
        return HideInputBarFlag;
    }

    public void setHideInputBarFlag(int hideInputBarFlag) {
        HideInputBarFlag = hideInputBarFlag;
    }

    public int getSex() {
        return Sex;
    }

    public void setSex(int sex) {
        Sex = sex;
    }

    public String getSignature() {
        return Signature;
    }

    public void setSignature(String signature) {
        Signature = signature;
    }

    public int getVerifyFlag() {
        return VerifyFlag;
    }

    public void setVerifyFlag(int verifyFlag) {
        VerifyFlag = verifyFlag;
    }

    public int getOwnerUin() {
        return OwnerUin;
    }

    public void setOwnerUin(int ownerUin) {
        OwnerUin = ownerUin;
    }

    public String getPYInitial() {
        return PYInitial;
    }

    public void setPYInitial(String PYInitial) {
        this.PYInitial = PYInitial;
    }

    public String getPYQuanPin() {
        return PYQuanPin;
    }

    public void setPYQuanPin(String PYQuanPin) {
        this.PYQuanPin = PYQuanPin;
    }

    public String getRemarkPYInitial() {
        return RemarkPYInitial;
    }

    public void setRemarkPYInitial(String remarkPYInitial) {
        RemarkPYInitial = remarkPYInitial;
    }

    public String getRemarkPYQuanPin() {
        return RemarkPYQuanPin;
    }

    public void setRemarkPYQuanPin(String remarkPYQuanPin) {
        RemarkPYQuanPin = remarkPYQuanPin;
    }

    public int getStarFriend() {
        return StarFriend;
    }

    public void setStarFriend(int starFriend) {
        StarFriend = starFriend;
    }

    public int getAppAccountFlag() {
        return AppAccountFlag;
    }

    public void setAppAccountFlag(int appAccountFlag) {
        AppAccountFlag = appAccountFlag;
    }

    public int getStatues() {
        return Statues;
    }

    public void setStatues(int statues) {
        Statues = statues;
    }

    public long getAttrStatus() {
        return AttrStatus;
    }

    public void setAttrStatus(int attrStatus) {
        AttrStatus = attrStatus;
    }

    public String getProvince() {
        return Province;
    }

    public void setProvince(String province) {
        Province = province;
    }

    public String getCity() {
        return City;
    }

    public void setCity(String city) {
        City = city;
    }

    public String getAlias() {
        return Alias;
    }

    public void setAlias(String alias) {
        Alias = alias;
    }

    public int getSnsFlag() {
        return SnsFlag;
    }

    public void setSnsFlag(int snsFlag) {
        SnsFlag = snsFlag;
    }

    public int getUniFriend() {
        return UniFriend;
    }

    public void setUniFriend(int uniFriend) {
        UniFriend = uniFriend;
    }

    public String getDisplayName() {
        return DisplayName;
    }

    public void setDisplayName(String displayName) {
        DisplayName = displayName;
    }

    public int getChatRoomId() {
        return ChatRoomId;
    }

    public void setChatRoomId(int chatRoomId) {
        ChatRoomId = chatRoomId;
    }

    public String getKeyWord() {
        return KeyWord;
    }

    public void setKeyWord(String keyWord) {
        KeyWord = keyWord;
    }

    public String getEncryChatRoomId() {
        return EncryChatRoomId;
    }

    public void setEncryChatRoomId(String encryChatRoomId) {
        EncryChatRoomId = encryChatRoomId;
    }

    public String getIsOwner() {
        return IsOwner;
    }

    public void setIsOwner(String isOwner) {
        IsOwner = isOwner;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(Uin);
        parcel.writeString(UserName);
        parcel.writeString(NickName);
        parcel.writeString(HeadImgUrl);
        parcel.writeInt(ContactFlag);
        parcel.writeInt(MemberCount);
        parcel.writeString(RemarkName);
        parcel.writeInt(HideInputBarFlag);
        parcel.writeInt(Sex);
        parcel.writeString(Signature);
        parcel.writeInt(VerifyFlag);
        parcel.writeInt(OwnerUin);
        parcel.writeString(PYInitial);
        parcel.writeString(PYQuanPin);
        parcel.writeString(RemarkPYInitial);
        parcel.writeString(RemarkPYQuanPin);
        parcel.writeInt(StarFriend);
        parcel.writeInt(AppAccountFlag);
        parcel.writeInt(Statues);
        parcel.writeLong(AttrStatus);
        parcel.writeString(Province);
        parcel.writeString(City);
        parcel.writeString(Alias);
        parcel.writeInt(SnsFlag);
        parcel.writeInt(UniFriend);
        parcel.writeString(DisplayName);
        parcel.writeInt(ChatRoomId);
        parcel.writeString(KeyWord);
        parcel.writeString(EncryChatRoomId);
        parcel.writeString(IsOwner);
    }
}
