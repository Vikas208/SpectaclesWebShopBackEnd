package com.example.SpectaclesWebShop.Bean;

public class ProductDescription {
       private String p_description;
       private String p_category; // lens,googles
       private String p_group;// men,women,kids
       private String p_frameStyle;
       private String p_frameSize;
       private String company_name;
       private String color;
       private String warranty;
       private String guaranty;

       public ProductDescription(String p_description, String p_category, String p_group, String p_frameStyle,
                     String company_name, String p_frameSize, String color, String warranty, String guaranty) {
              this.p_description = p_description;
              this.p_category = p_category;
              this.p_group = p_group;
              this.p_frameStyle = p_frameStyle;
              this.company_name = company_name;
              this.p_frameSize = p_frameSize;
              this.color = color;
              this.warranty = warranty;
              this.guaranty = guaranty;

       }

       public String getGuaranty() {
              return guaranty;
       }

       public void setGuaranty(String guaranty) {
              this.guaranty = guaranty;
       }

       public String getWarranty() {
              return warranty;
       }

       public void setWarranty(String warranty) {
              this.warranty = warranty;
       }

       public String getColor() {
              return color;
       }

       public void setColor(String color) {
              this.color = color;
       }

       public ProductDescription() {
       }

       public String getP_frameSize() {
              return p_frameSize;
       }

       public void setP_frameSize(String p_frameSize) {
              this.p_frameSize = p_frameSize;
       }

       public String getP_description() {
              return p_description;
       }

       public void setP_description(String p_description) {
              this.p_description = p_description;
       }

       public String getP_category() {
              return p_category;
       }

       public void setP_category(String p_category) {
              this.p_category = p_category;
       }

       public String getP_group() {
              return p_group;
       }

       public void setP_group(String p_group) {
              this.p_group = p_group;
       }

       public String getP_frameStyle() {
              return p_frameStyle;
       }

       public void setP_frameStyle(String p_frameStyle) {
              this.p_frameStyle = p_frameStyle;
       }

       public String getCompany_name() {
              return company_name;
       }

       public void setCompany_name(String company_name) {
              this.company_name = company_name;
       }

       @Override
       public String toString() {
              return "ProductDescription [color=" + color + ", company_name=" + company_name + ", p_category="
                            + p_category + ", p_description=" + p_description + ", p_frameSize=" + p_frameSize
                            + ", p_frameStyle=" + p_frameStyle + ", p_group=" + p_group + "]";
       }

}
