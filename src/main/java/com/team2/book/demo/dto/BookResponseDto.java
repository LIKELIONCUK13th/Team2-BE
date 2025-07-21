//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.team2.book.demo.dto;

import lombok.Generated;

public class BookResponseDto {
    private String title;
    private String author;
    private String publisher;
    private String year;

    @Generated
    public String getTitle() {
        return this.title;
    }

    @Generated
    public String getAuthor() {
        return this.author;
    }

    @Generated
    public String getPublisher() {
        return this.publisher;
    }

    @Generated
    public String getYear() {
        return this.year;
    }

    @Generated
    public void setTitle(final String title) {
        this.title = title;
    }

    @Generated
    public void setAuthor(final String author) {
        this.author = author;
    }

    @Generated
    public void setPublisher(final String publisher) {
        this.publisher = publisher;
    }

    @Generated
    public void setYear(final String year) {
        this.year = year;
    }

    @Generated
    public boolean equals(final Object o) {
        if (o == this) {
            return true;
        } else if (!(o instanceof BookResponseDto)) {
            return false;
        } else {
            BookResponseDto other = (BookResponseDto)o;
            if (!other.canEqual(this)) {
                return false;
            } else {
                label59: {
                    Object this$title = this.getTitle();
                    Object other$title = other.getTitle();
                    if (this$title == null) {
                        if (other$title == null) {
                            break label59;
                        }
                    } else if (this$title.equals(other$title)) {
                        break label59;
                    }

                    return false;
                }

                Object this$author = this.getAuthor();
                Object other$author = other.getAuthor();
                if (this$author == null) {
                    if (other$author != null) {
                        return false;
                    }
                } else if (!this$author.equals(other$author)) {
                    return false;
                }

                Object this$publisher = this.getPublisher();
                Object other$publisher = other.getPublisher();
                if (this$publisher == null) {
                    if (other$publisher != null) {
                        return false;
                    }
                } else if (!this$publisher.equals(other$publisher)) {
                    return false;
                }

                Object this$year = this.getYear();
                Object other$year = other.getYear();
                if (this$year == null) {
                    if (other$year != null) {
                        return false;
                    }
                } else if (!this$year.equals(other$year)) {
                    return false;
                }

                return true;
            }
        }
    }

    @Generated
    protected boolean canEqual(final Object other) {
        return other instanceof BookResponseDto;
    }

    @Generated
    public int hashCode() {
        int PRIME = true;
        int result = 1;
        Object $title = this.getTitle();
        result = result * 59 + ($title == null ? 43 : $title.hashCode());
        Object $author = this.getAuthor();
        result = result * 59 + ($author == null ? 43 : $author.hashCode());
        Object $publisher = this.getPublisher();
        result = result * 59 + ($publisher == null ? 43 : $publisher.hashCode());
        Object $year = this.getYear();
        result = result * 59 + ($year == null ? 43 : $year.hashCode());
        return result;
    }

    @Generated
    public String toString() {
        String var10000 = this.getTitle();
        return "BookResponseDto(title=" + var10000 + ", author=" + this.getAuthor() + ", publisher=" + this.getPublisher() + ", year=" + this.getYear() + ")";
    }

    @Generated
    public BookResponseDto(final String title, final String author, final String publisher, final String year) {
        this.title = title;
        this.author = author;
        this.publisher = publisher;
        this.year = year;
    }

    @Generated
    public BookResponseDto() {
    }
}
