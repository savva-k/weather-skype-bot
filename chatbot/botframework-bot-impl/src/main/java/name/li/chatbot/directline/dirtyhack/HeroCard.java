package name.li.chatbot.directline.dirtyhack;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.client.model.CardAction;
import io.swagger.client.model.CardImage;
import io.swagger.client.model.Object;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * A Hero card (card with a single, large image)
 */
public class HeroCard extends Object {
  @JsonProperty("title")
  private String title = null;

  @JsonProperty("subtitle")
  private String subtitle = null;

  @JsonProperty("text")
  private String text = null;

  @JsonProperty("images")
  private List<CardImage> images = null;

  @JsonProperty("buttons")
  private List<CardAction> buttons = null;

  @JsonProperty("tap")
  private CardAction tap = null;

  public HeroCard title(String title) {
    this.title = title;
    return this;
  }

   /**
   * Title of the card
   * @return title
  **/
  @ApiModelProperty(value = "Title of the card")
  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public HeroCard subtitle(String subtitle) {
    this.subtitle = subtitle;
    return this;
  }

   /**
   * Subtitle of the card
   * @return subtitle
  **/
  @ApiModelProperty(value = "Subtitle of the card")
  public String getSubtitle() {
    return subtitle;
  }

  public void setSubtitle(String subtitle) {
    this.subtitle = subtitle;
  }

  public HeroCard text(String text) {
    this.text = text;
    return this;
  }

   /**
   * Text for the card
   * @return text
  **/
  @ApiModelProperty(value = "Text for the card")
  public String getText() {
    return text;
  }

  public void setText(String text) {
    this.text = text;
  }

  public HeroCard images(List<CardImage> images) {
    this.images = images;
    return this;
  }

  public HeroCard addImagesItem(CardImage imagesItem) {
    if (this.images == null) {
      this.images = new ArrayList<CardImage>();
    }
    this.images.add(imagesItem);
    return this;
  }

   /**
   * Array of images for the card
   * @return images
  **/
  @ApiModelProperty(value = "Array of images for the card")
  public List<CardImage> getImages() {
    return images;
  }

  public void setImages(List<CardImage> images) {
    this.images = images;
  }

  public HeroCard buttons(List<CardAction> buttons) {
    this.buttons = buttons;
    return this;
  }

  public HeroCard addButtonsItem(CardAction buttonsItem) {
    if (this.buttons == null) {
      this.buttons = new ArrayList<CardAction>();
    }
    this.buttons.add(buttonsItem);
    return this;
  }

   /**
   * Set of actions applicable to the current card
   * @return buttons
  **/
  @ApiModelProperty(value = "Set of actions applicable to the current card")
  public List<CardAction> getButtons() {
    return buttons;
  }

  public void setButtons(List<CardAction> buttons) {
    this.buttons = buttons;
  }

  public HeroCard tap(CardAction tap) {
    this.tap = tap;
    return this;
  }

   /**
   * This action will be activated when user taps on the card itself
   * @return tap
  **/
  @ApiModelProperty(value = "This action will be activated when user taps on the card itself")
  public CardAction getTap() {
    return tap;
  }

  public void setTap(CardAction tap) {
    this.tap = tap;
  }


  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    HeroCard heroCard = (HeroCard) o;
    return Objects.equals(this.title, heroCard.title) &&
        Objects.equals(this.subtitle, heroCard.subtitle) &&
        Objects.equals(this.text, heroCard.text) &&
        Objects.equals(this.images, heroCard.images) &&
        Objects.equals(this.buttons, heroCard.buttons) &&
        Objects.equals(this.tap, heroCard.tap);
  }

  @Override
  public int hashCode() {
    return Objects.hash(title, subtitle, text, images, buttons, tap);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class HeroCard {\n");

    sb.append("    title: ").append(toIndentedString(title)).append("\n");
    sb.append("    subtitle: ").append(toIndentedString(subtitle)).append("\n");
    sb.append("    text: ").append(toIndentedString(text)).append("\n");
    sb.append("    images: ").append(toIndentedString(images)).append("\n");
    sb.append("    buttons: ").append(toIndentedString(buttons)).append("\n");
    sb.append("    tap: ").append(toIndentedString(tap)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(java.lang.Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
  
}

