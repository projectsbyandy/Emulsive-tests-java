package andycprojects.models.product;

public record ProductDetail(int id, String imageUrl, String name, String priceWithCurrency, String detailsUrlPart, String iso, String format, String manufacturer, String description) {
}