package ml.squidnet.enums;

public enum QueryURL {
  LIVE_URL("https://politicsandwar.com/api/"),
  TEST_URL("https://test.politicsandwar.com/api/"),
  NATION_URL("nation/"),
  NATIONS_URL("nations/"),
  ALLIANCE_URL("alliance/"),
  ALLIANCES_URL("alliances/"),
  WAR_URL("war/"),
  WARS_URL("wars/"),
  BANK_URL("alliance-bank/"),
  MEMBERS_URL("alliance-members/"),
  APPLICANTS_URL("applicants/"),
  CITY_URL("city/"),
  TRADEPRICE_URL("tradeprice/");

  private final String url;

  QueryURL(final String url) {
    this.url = url;
  }

  public String getUrl() {
    return url;
  }
}