package com.mycompany.app;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "library_asset")
public class LibraryAsset {
    @Column(name = "asset_id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Getter @Setter
    @Column(name = "asset_code", unique = true, nullable = false)
    private String assetCode;

    @Getter @Setter
    @Column(name = "asset_title", nullable = false)
    private String assetTitle;

    @Getter @Setter
    @Column(name = "creator_name", nullable = false)
    private String creatorName;

    @Getter @Setter
    @Column(name = "publisher_name", nullable = false)
    private String publisherName;

    @Getter @Setter
    @Column(name = "asset_category", nullable = false)
    private String assetCategory;

    @Getter @Setter
    @Column(name = "asset_type", nullable = false)
    private String assetType;

    @Getter @Setter
    @Column(name = "edition_year", nullable = false)
    private Short editionYear;

    @Getter @Setter
    @Column(name = "price_usd", nullable = false)
    private String priceUSD;
}
