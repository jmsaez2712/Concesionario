package dev.jmsaez.concesionariofragment;

import android.os.Parcel;
import android.os.Parcelable;

public class Car implements Parcelable {
     private int ref;
     private String title;
     private String desc;
     private String tags;
     private String url;
     private String imgs;
     private int price;

    public Car(int ref, String title, String desc, String tags, String url, String imgs, int price) {
        this.ref = ref;
        this.title = title;
        this.desc = desc;
        this.tags = tags;
        this.url = url;
        this.imgs = imgs;
        this.price = price;
    }

    protected Car(Parcel in) {
        ref = in.readInt();
        title = in.readString();
        desc = in.readString();
        tags = in.readString();
        url = in.readString();
        imgs = in.readString();
        price = in.readInt();
    }

    public static final Creator<Car> CREATOR = new Creator<Car>() {
        @Override
        public Car createFromParcel(Parcel in) {
            return new Car(in);
        }

        @Override
        public Car[] newArray(int size) {
            return new Car[size];
        }
    };

    @Override
    public String toString() {
        return "Car{" +
                "ref=" + ref +
                ", title='" + title + '\'' +
                ", desc='" + desc + '\'' +
                ", tags='" + tags + '\'' +
                ", url='" + url + '\'' +
                ", imgs='" + imgs + '\'' +
                ", price=" + price +
                '}';
    }

    public int getRef() {
        return ref;
    }

    public void setRef(int ref) {
        this.ref = ref;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getImgs() {
        return imgs;
    }

    public void setImgs(String imgs) {
        this.imgs = imgs;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(ref);
        parcel.writeString(title);
        parcel.writeString(desc);
        parcel.writeString(tags);
        parcel.writeString(url);
        parcel.writeString(imgs);
        parcel.writeInt(price);
    }
}
