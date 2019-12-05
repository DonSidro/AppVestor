package com.calldorado.appvestor.utils;

public class JsonErrorCodes {


    private static final int OK = 0;
    private static final int INVALID_PUBLISHER_ID = 10;
    private static final int INVALID_BINARY_ID = 11;
    private static final int INVALID_CLIENT_ID = 12;
    private static final int INVALID_INVESTMENT_FUND_ID = 200;
    private static final int INVALID_INVESTMENT_FUND_STATE = 201;
    private static final int INVALID_INVESTOR_INVEST_AMOUNT = 210;
    private static final int INSUFFICIENT_FUNDS = 211;
    private static final int INVALID_INVESTOR_REDEEM = 212;
    private static final int INVALID_ADMIN_COMMAND = 500;
    private static final int INVALID_ADMINISTRATOR_ID = 501;
    private static final int INVALID_USER_ID = 502;
    private static final int INACTIVE_USER_ID = 503;
    private static final int INVALID_ACTIVATE_TOKEN = 504;
    private static final int INVALID_USER_NAME = 505;
    private static final int USER_NAME_IN_USE = 506;
    private static final int LOGIN_FAILED = 508;
    private static final int INVALID_PUBLISHER_TYPE = 510;
    private static final int ACCOUNT_NOT_APPROVED = 519;
    private static final int INVALID_PACKAGE_NAME = 524;
    private static final int PACKAGE_NAME_IN_USE = 525;
    private static final int INVALID_TEMPLATE_NAME = 527;
    private static final int TEMPLATE_NAME_IN_USE = 528;
    private static final int EMAIL_ADDRESS_IN_USE = 549;
    private static final int EMAIL_NOT_VERIFIED = 556;
    private static final int INACTIVE_ACCOUNT_ID = 558;
    private static final int COMPANY_NAME_IN_USE = 567;
    private static final int ENGAGEMENT_NAME_IN_USE = 574;
    private static final int MISSING_DATA = 700;
    private static final int UNKNOWN_ERROR = 999;


    public static JsonRequestResponse checkForPostRequest(int i){

        switch (i){

            case OK:
                return new JsonRequestResponse(OK,"Ok.");
            case INVALID_PUBLISHER_ID:
                return new JsonRequestResponse(INVALID_PUBLISHER_ID,"Invalid publisher ID.");
            case INVALID_BINARY_ID:
                return new JsonRequestResponse(INVALID_BINARY_ID,"Invalid binary ID.");
            case INVALID_CLIENT_ID:
                return new JsonRequestResponse(INVALID_CLIENT_ID,"Invalid client ID.");
            case INVALID_INVESTMENT_FUND_ID:
                return new JsonRequestResponse(INVALID_INVESTMENT_FUND_ID,"There is no fund raising round with the given ID.");
            case INVALID_INVESTMENT_FUND_STATE:
                return new JsonRequestResponse(INVALID_INVESTMENT_FUND_STATE,"The fund raising round is not open.");
            case INVALID_INVESTOR_INVEST_AMOUNT:
                return new JsonRequestResponse(INVALID_INVESTOR_INVEST_AMOUNT,"An unpermitted amount has been entered.");
            case INSUFFICIENT_FUNDS:
                return new JsonRequestResponse(INSUFFICIENT_FUNDS,"There are insufficient funds for this transaction.");
            case INVALID_INVESTOR_REDEEM:
                return new JsonRequestResponse(INVALID_INVESTOR_REDEEM,"Only one redeem-action is allowed per day.");
            case INVALID_ADMIN_COMMAND:
                return new JsonRequestResponse(INVALID_ADMIN_COMMAND,"Invalid admin command");
            case INVALID_ADMINISTRATOR_ID:
                return new JsonRequestResponse(INVALID_ADMINISTRATOR_ID,"Invalid administrator ID");
            case INVALID_USER_ID:
                return new JsonRequestResponse(INVALID_USER_ID,"Invalid user ID");
            case INACTIVE_USER_ID:
                return new JsonRequestResponse(INACTIVE_USER_ID,"Inactive user ID");
            case INVALID_ACTIVATE_TOKEN:
                return new JsonRequestResponse(INVALID_ACTIVATE_TOKEN,"Invalid activate token. This user may have already been activated.");
            case INVALID_USER_NAME:
                return new JsonRequestResponse(INVALID_USER_NAME,"Invalid username.");
            case USER_NAME_IN_USE:
                return new JsonRequestResponse(USER_NAME_IN_USE,"Username already in use.");
            case LOGIN_FAILED:
                return new JsonRequestResponse(LOGIN_FAILED,"Wrong email or password.");
            case INVALID_PUBLISHER_TYPE:
                return new JsonRequestResponse(INVALID_PUBLISHER_TYPE,"Invalid publisher type.");
            case ACCOUNT_NOT_APPROVED:
                return new JsonRequestResponse(ACCOUNT_NOT_APPROVED,"Your account is under approval.");
            case INVALID_PACKAGE_NAME:
                return new JsonRequestResponse(INVALID_PACKAGE_NAME,"Invalid package name! Please change your package name.");
            case PACKAGE_NAME_IN_USE:
                return new JsonRequestResponse(PACKAGE_NAME_IN_USE,"Package name in use! Please change your package name.");
            case INVALID_TEMPLATE_NAME:
                return new JsonRequestResponse(INVALID_TEMPLATE_NAME,"Invalid template name! Please change your template name.");
            case TEMPLATE_NAME_IN_USE:
                return new JsonRequestResponse(TEMPLATE_NAME_IN_USE,"Template name in use! Please change your template name.");
            case EMAIL_ADDRESS_IN_USE:
                return new JsonRequestResponse(EMAIL_ADDRESS_IN_USE,"Email address in use! Please choose another!");
            case EMAIL_NOT_VERIFIED:
                return new JsonRequestResponse(EMAIL_NOT_VERIFIED,"Email not verified. Please Check your email for verification mail!");
            case INACTIVE_ACCOUNT_ID:
                return new JsonRequestResponse(INACTIVE_ACCOUNT_ID,"The account ID is inactive.");
            case COMPANY_NAME_IN_USE:
                return new JsonRequestResponse(COMPANY_NAME_IN_USE,"This company is already registered!");
            case ENGAGEMENT_NAME_IN_USE:
                return new JsonRequestResponse(ENGAGEMENT_NAME_IN_USE,"Engagement field name already in use!");
            case MISSING_DATA:
                return new JsonRequestResponse(MISSING_DATA,"Missing data.");
            case UNKNOWN_ERROR:
                return new JsonRequestResponse(UNKNOWN_ERROR,"An error has occurred!");

        }
        return new JsonRequestResponse(0,"");

    }


}
