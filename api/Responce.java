package com.scrapshubvendor.api;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Responce {

    public class SignupMember {
        @SerializedName("message")
        @Expose
        private String message;
        @SerializedName("status")
        @Expose
        private Boolean status;
        @SerializedName("data")
        @Expose
        private List<Data> data = null;

        public String getMessage() {
            return message;
        }

        public Boolean getStatus() {
            return status;
        }

        public List<Data> getData() {
            return data;
        }

        public class Data {
            @SerializedName("Id")
            @Expose
            private String id;
            @SerializedName("name")
            @Expose
            private String name;
            @SerializedName("email")
            @Expose
            private String email;
            @SerializedName("phone_number")
            @Expose
            private String phoneNumber;
            @SerializedName("password")
            @Expose
            private String password;
            @SerializedName("city")
            @Expose
            private String city;

            public String getAddress() {
                return address;
            }

            @SerializedName("address")
            @Expose
            private String address;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getName() {
                return name;
            }


            public String getEmail() {
                return email;
            }


            public String getPhoneNumber() {
                return phoneNumber;
            }


            public String getPassword() {
                return password;
            }

            public String getCity() {
                return city;
            }

        }
    }

    public class FetchProduct {
        @SerializedName("message")
        @Expose
        private String message;
        @SerializedName("status")
        @Expose
        private Boolean status;
        @SerializedName("data")
        @Expose
        private List<Data> data = null;

        public String getMessage() {
            return message;
        }

        public Boolean getStatus() {
            return status;
        }

        public List<Data> getData() {
            return data;
        }

        public class Data {
            @SerializedName("Id")
            @Expose
            private String id;
            @SerializedName("product")
            @Expose
            private String product;
            @SerializedName("image")
            @Expose
            private String image;
            @SerializedName("subcategory")
            @Expose
            private List<Subcategory> subcategory = null;

            public String getId() {
                return id;
            }

            public String getProduct() {
                return product;
            }

            public String getImage() {
                return image;
            }

            public List<Subcategory> getSubcategory() {
                return subcategory;
            }

            public class Subcategory {
                @SerializedName("Id")
                @Expose
                private String id;
                @SerializedName("category")
                @Expose
                private String category;

                @SerializedName("isChecked")
                @Expose
                private Boolean isChecked = false;

                public Boolean getChecked() {
                    return isChecked;
                }

                public void setChecked(Boolean checked) {
                    isChecked = checked;
                }


                public String getId() {
                    return id;
                }

                public String getCategory() {
                    return category;
                }

            }
        }

    }

    public class FetchCity {

        @SerializedName("message")
        @Expose
        private String message;
        @SerializedName("status")
        @Expose
        private Boolean status;
        @SerializedName("data")
        @Expose
        private List<Data> data = null;

        public String getMessage() {
            return message;
        }

        public Boolean getStatus() {
            return status;
        }

        public List<Data> getData() {
            return data;
        }


        public class Data {

            @SerializedName("id")
            @Expose
            private String id;
            @SerializedName("cityname")
            @Expose
            private String cityname;

            public String getId() {
                return id;
            }

            public String getCityname() {
                return cityname;
            }
        }
    }

    public class Status {
        @SerializedName("message")
        @Expose
        private String message;

        public String getMessage() {
            return message;
        }

        public Boolean getStatus() {
            return status;
        }

        @SerializedName("status")
        @Expose
        private Boolean status;

    }

    public class History {

        @SerializedName("message")
        @Expose
        private String message;
        @SerializedName("status")
        @Expose
        private Boolean status;
        @SerializedName("data")
        @Expose
        private List<Data> data = null;

        public String getMessage() {
            return message;
        }


        public Boolean getStatus() {
            return status;
        }


        public List<Data> getData() {
            return data;
        }

        public class Data {

            @SerializedName("historyid")
            @Expose
            private String historyid;
            @SerializedName("name")
            @Expose
            private String name;
            @SerializedName("walletid")
            @Expose
            private String walletid;
            @SerializedName("saleid")
            @Expose
            private String saleid;
            @SerializedName("purchaseid")
            @Expose
            private String purchaseid;
            @SerializedName("purchase_value")
            @Expose
            private String purchaseValue;
            @SerializedName("date")
            @Expose
            private String date;
            @SerializedName("purchasename")
            @Expose
            private String purchasename;
            @SerializedName("userid")
            @Expose
            private String userid;
            @SerializedName("phoneno")
            @Expose
            private String phoneno;
            @SerializedName("email")
            @Expose
            private String email;
            @SerializedName("password")
            @Expose
            private String password;
            @SerializedName("otp")
            @Expose
            private String otp;
            @SerializedName("paasport")
            @Expose
            private String paasport;
            @SerializedName("deviceid")
            @Expose
            private String deviceid;
            @SerializedName("isactive")
            @Expose
            private String isactive;

            @SerializedName("art_gallery_name")
            @Expose
            private String art_gallery_name;

            public String getArtpic() {
                return artpic;
            }

            @SerializedName("artpic")
            @Expose
            private String artpic;
            public String getValue() {
                return value;
            }

            @SerializedName("value")
            @Expose
            private String value;

            public String getArt_gallery_name() {
                return art_gallery_name;
            }

            public String getPurchase_date() {
                return purchase_date;
            }

            @SerializedName("purchase_date")
            @Expose
            private String purchase_date;

            public String getHistoryid() {
                return historyid;
            }


            public String getName() {
                return name;
            }


            public String getWalletid() {
                return walletid;
            }



            public String getSaleid() {
                return saleid;
            }



            public String getPurchaseid() {
                return purchaseid;
            }


            public String getPurchaseValue() {
                return purchaseValue;
            }


            public String getDate() {
                return date;
            }


            public String getPurchasename() {
                return purchasename;
            }



            public String getUserid() {
                return userid;
            }


            public String getPhoneno() {
                return phoneno;
            }


            public String getEmail() {
                return email;
            }


            public String getPassword() {
                return password;
            }


            public String getOtp() {
                return otp;
            }



            public String getPaasport() {
                return paasport;
            }



            public String getDeviceid() {
                return deviceid;
            }


            public String getIsactive() {
                return isactive;
            }


        }
    }
    public class FeatchArt {
        @SerializedName("message")
        @Expose
        private String message;
        @SerializedName("status")
        @Expose
        private Boolean status;
        @SerializedName("data")
        @Expose
        private List<Data> data = null;

        public String getMessage() {
            return message;
        }
       public Boolean getStatus() {
            return status;
        }

        public List<Data> getData() {
            return data;
        }
        public class Data {
            @SerializedName("artid")
            @Expose
            private String artid;
            @SerializedName("userid")
            @Expose
            private String userid;
            @SerializedName("artpic")
            @Expose
            private String artpic;
            @SerializedName("qr_code")
            @Expose
            private String qrCode;
            @SerializedName("painter_name")
            @Expose
            private String painterName;
            @SerializedName("owner_name")
            @Expose
            private String ownerName;
            @SerializedName("art_gallery_name")
            @Expose
            private String artGalleryName;
            @SerializedName("dop")
            @Expose
            private String dop;
            @SerializedName("value")
            @Expose
            private String value;
            @SerializedName("art_type")
            @Expose
            private String artType;
            @SerializedName("date")
            @Expose
            private String date;
            @SerializedName("isactive")
            @Expose
            private String isactive;

            public String getArtid() {
                return artid;
            }
            public String getUserid() {
                return userid;
            }
            public String getArtpic() {
                return artpic;
            }
            public String getQrCode() {
                return qrCode;
            }
            public String getPainterName() {
                return painterName;
            }
            public String getOwnerName() {
                return ownerName;
            }
            public String getArtGalleryName() {
                return artGalleryName;
            }
            public String getDop() {
                return dop;
            }
            public String getValue() {
                return value;
            }
            public String getArtType() {
                return artType;
            }
            public String getDate() {
                return date;
            }
            public String getIsactive() {
                return isactive;
            }
            }
        }

    public class FeatProfile {

        @SerializedName("message")
        @Expose
        private String message;
        @SerializedName("status")
        @Expose
        private Boolean status;
        @SerializedName("data")
        @Expose
        private List<Data> data = null;

        public String getMessage() {
            return message;
        }

        public Boolean getStatus() {
            return status;
        }


        public List<Data> getData() {
            return data;
        }

        public class Data {

            @SerializedName("userid")
            @Expose
            private String userid;
            @SerializedName("name")
            @Expose
            private String name;
            @SerializedName("phoneno")
            @Expose
            private String phoneno;
            @SerializedName("email")
            @Expose
            private String email;
            @SerializedName("password")
            @Expose
            private String password;
            @SerializedName("otp")
            @Expose
            private String otp;
            @SerializedName("paasport")
            @Expose
            private String paasport;
            @SerializedName("deviceid")
            @Expose
            private String deviceid;
            @SerializedName("date")
            @Expose
            private String date;
            @SerializedName("isactive")
            @Expose
            private String isactive;

            public String getUserid() {
                return userid;
            }
            public String getName() {
                return name;
            }
            public String getPhoneno() {
                return phoneno;
            }
            public String getEmail() {
                return email;
            }
            public String getPassword() {
                return password;
            }
            public String getOtp() {
                return otp;
            }
            public String getPaasport() {
                return paasport;
            }
            public String getDeviceid() {
                return deviceid;
            }
            public String getDate() {
                return date;
            }
            public String getIsactive() {
                return isactive;
            }
            }
    }
   public class LatLangBounds{
       @SerializedName("candidates")
       @Expose
       private List<Candidate> candidates = null;
       @SerializedName("status")
       @Expose
       private String status;

       public List<Candidate> getCandidates() {
           return candidates;
       }
       public String getStatus() {
           return status;
       }
       public class Candidate {
           @SerializedName("geometry")
           @Expose
           private Geometry geometry;
           public Geometry getGeometry() {
               return geometry;
           }
           public class Geometry {
               @SerializedName("location")
               @Expose
               private Location location;
               public Location location() {
                   return location;
               }
               public class Location {
                   @SerializedName("lat")
                   @Expose
                   private Double lat;
                   @SerializedName("lng")
                   @Expose
                   private Double lng;

                   public Double getLat() {
                       return lat;
                   }
                   public Double getLng() {
                       return lng;
                   }
               }
               }
       }
   }


    public class FetchVendorOrders {

        @SerializedName("message")
        @Expose
        private String message;
        @SerializedName("status")
        @Expose
        private Boolean status;
        @SerializedName("data")
        @Expose
        private List<Data> data = null;

        public String getMessage() {
            return message;
        }



        public Boolean getStatus() {
            return status;
        }



        public List<Data> getData() {
            return data;
        }



        public class Data {

            @SerializedName("Id")
            @Expose
            private String id;
            @SerializedName("name")
            @Expose
            private String name;
            @SerializedName("phone")
            @Expose
            private String phone;
            @SerializedName("city")
            @Expose
            private String city;
            @SerializedName("address")
            @Expose
            private String address;
            @SerializedName("lat")
            @Expose
            private String lat;
            @SerializedName("log")
            @Expose
            private String log;
            @SerializedName("product")
            @Expose
            private String product;
            @SerializedName("creadit")
            @Expose
            private String creadit;
            @SerializedName("venderId")
            @Expose
            private String venderId;
            @SerializedName("status")
            @Expose
            private String status;
            @SerializedName("adddate")
            @Expose
            private String adddate;
            @SerializedName("addtime")
            @Expose
            private String addtime;
            @SerializedName("accepted_date")
            @Expose
            private String acceptedDate;
            @SerializedName("completed_date")
            @Expose
            private String completedDate;
            @SerializedName("userid")
            @Expose
            private String userid;
            @SerializedName("productcategory")
            @Expose
            private String productcategory;
            @SerializedName("date")
            @Expose
            private String date;

            public String getOrderid() {
                return orderid;
            }

            @SerializedName("orderid")
            @Expose
            private String orderid;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getName() {
                return name;
            }



            public String getPhone() {
                return phone;
            }

            public String getCity() {
                return city;
            }



            public String getAddress() {
                return address;
            }



            public String getLat() {
                return lat;
            }

            public String getLog() {
                return log;
            }


            public String getProduct() {
                return product;
            }



            public String getCreadit() {
                return creadit;
            }



            public String getVenderId() {
                return venderId;
            }


            public String getStatus() {
                return status;
            }

            public String getAdddate() {
                return adddate;
            }

            public String getAddtime() {
                return addtime;
            }


            public String getAcceptedDate() {
                return acceptedDate;
            }



            public String getCompletedDate() {
                return completedDate;
            }


            public String getUserid() {
                return userid;
            }


            public String getProductcategory() {
                return productcategory;
            }


            public String getDate() {
                return date;
            }
        }
    }

    public class FetchVendorLeaderCredits {
        @SerializedName("message")
        @Expose
        private String message;
        @SerializedName("status")
        @Expose
        private Boolean status;
        @SerializedName("data")
        @Expose
        private List<Data> data = null;

        public String getMessage() {
            return message;
        }
        public Boolean getStatus() {
            return status;
        }
        public List<Data> getData() {
            return data;
        }

        public class Data {

            @SerializedName("Id")
            @Expose
            private String id;
            @SerializedName("orderId")
            @Expose
            private String orderId;
            @SerializedName("vendorId")
            @Expose
            private String vendorId;
            @SerializedName("credit")
            @Expose
            private String credit;
            @SerializedName("expense")
            @Expose
            private String expense;
            @SerializedName("rem_credit")
            @Expose
            private String remCredit;

            @SerializedName("date")
            @Expose
            private String date;

            public String getPrice() {
                return price;
            }

            @SerializedName("price")
            @Expose
            private String price;

            public String getId() {
                return id;
            }
            public String getOrderId() {
                return orderId;
            }
            public String getVendorId() {
                return vendorId;
            }
            public String getCredit() {
                return credit;
            }
            public String getExpense() {
                return expense;
            }

            public String getRemCredit() {
                return remCredit;
            }
            public String getDate() {
                return date;
            }


        }

    }

    public class FetchOrderDetail {
        @SerializedName("message")
        @Expose
        private String message;
        @SerializedName("status")
        @Expose
        private Boolean status;
        @SerializedName("data")
        @Expose
        private List<Data> data = null;

        public String getMessage() {
            return message;
        }

        public Boolean getStatus() {
            return status;
        }
        public List<Data> getData() {
            return data;
        }
        public class Data {
            @SerializedName("Id")
            @Expose
            private String id;
            @SerializedName("name")
            @Expose
            private String name;
            @SerializedName("phone")
            @Expose
            private String phone;
            @SerializedName("city")
            @Expose
            private String city;
            @SerializedName("address")
            @Expose
            private String address;
            @SerializedName("lat")
            @Expose
            private String lat;
            @SerializedName("log")
            @Expose
            private String log;
            @SerializedName("product")
            @Expose
            private String product;
            @SerializedName("creadit")
            @Expose
            private String creadit;
            @SerializedName("venderId")
            @Expose
            private String venderId;
            @SerializedName("status")
            @Expose
            private String status;
            @SerializedName("adddate")
            @Expose
            private String adddate;
            @SerializedName("addtime")
            @Expose
            private String addtime;
            @SerializedName("accepted_date")
            @Expose
            private String acceptedDate;
            @SerializedName("completed_date")
            @Expose
            private String completedDate;
            @SerializedName("userid")
            @Expose
            private String userid;
            @SerializedName("productcategory")
            @Expose
            private String productcategory;
            @SerializedName("date")
            @Expose
            private String date;

            public String getProductname() {
                return productname;
            }

            @SerializedName("productname")
            @Expose
            private String productname;

            public String getId() {
                return id;
            }
            public String getName() {
                return name;
            }
            public String getPhone() {
                return phone;
            }
            public String getCity() {
                return city;
            }
            public String getAddress() {
                return address;
            }
            public String getLat() {
                return lat;
            }
            public String getLog() {
                return log;
            }
            public String getProduct() {
                return product;
            }
            public String getCreadit() {
                return creadit;
            }
            public String getVenderId() {
                return venderId;
            }
            public String getStatus() {
                return status;
            }
            public String getAdddate() {
                return adddate;
            }
            public String getAddtime() {
                return addtime;
            }
            public String getAcceptedDate() {
                return acceptedDate;
            }
            public String getCompletedDate() {
                return completedDate;
            }
            public String getUserid() {
                return userid;
            }
            public String getProductcategory() {
                return productcategory;
            }
            public String getDate() {
                return date;
            }
        }
    }

    public class FetchPackage {
        @SerializedName("message")
        @Expose
        private String message;
        @SerializedName("status")
        @Expose
        private Boolean status;
        @SerializedName("data")
        @Expose
        private List<Data> data = null;

        public String getMessage() {
            return message;
        }
        public Boolean getStatus() {
            return status;
        }
        public List<Data> getData() {
            return data;
        }
        public class Data {
            @SerializedName("Id")
            @Expose
            private String id;
            @SerializedName("title")
            @Expose
            private String title;
            @SerializedName("disc")
            @Expose
            private String disc;
            @SerializedName("credit")
            @Expose
            private String credit;
            @SerializedName("price")
            @Expose
            private String price;
            @SerializedName("isActive")
            @Expose
            private String isActive;

            public String getId() {
                return id;
            }
            public String getTitle() {
                return title;
            }
            public String getDisc() {
                return disc;
            }
            public String getCredit() {
                return credit;
            }
            public String getPrice() {
                return price;
            }
            public String getIsActive() {
                return isActive;
            }
        }
    }
    public class FetchAboutus {
        @SerializedName("message")
        @Expose
        private String message;
        @SerializedName("status")
        @Expose
        private Boolean status;
        @SerializedName("data")
        @Expose
        private List<Data> data = null;

        public String getMessage() {
            return message;
        }
        public Boolean getStatus() {
            return status;
        }
        public List<Data> getData() {
            return data;
        }

        public class Data {
            @SerializedName("Id")
            @Expose
            private String id;
            @SerializedName("aboutus")
            @Expose
            private String aboutus;
            @SerializedName("isActive")
            @Expose
            private String isActive;

            public String getId() {
                return id;
            }
            public String getAboutus() {
                return aboutus;
            }
            public String getIsActive() {
                return isActive;
            }
        }

    }
    public class FetchUserStatus {
        @SerializedName("message")
        @Expose
        private String message;
        @SerializedName("status")
        @Expose
        private Boolean status;
        @SerializedName("data")
        @Expose
        private List<Data> data = null;

        public String getMessage() {
            return message;
        }
        public Boolean getStatus() {
            return status;
        }
        public List<Data> getData() {
            return data;
        }

        public class Data {

            public String getOn_off() {
                return on_off;
            }

            @SerializedName("on_off")
            @Expose
            private String on_off;


        }

    }
}






