package smyts.lab6.utils;

import smyts.lab6.common.util.Response;

public class ResponseHandler {

    public void getResponseInfo(Response response) {
        if (response.getMessage() != null) {
            System.out.println(response.getMessage());
        }
        if (response.getRoutes() != null && response.getRoutes().size() > 0) {
            for (int i = 0; i < response.getRoutes().size(); i++) {
                System.out.println(response.getRoutes().get(i).toString());
            }
        }

        if (response.getStrings().size() > 0) {
            for (int i = 0; i < response.getStrings().size(); i++) {
                System.out.println(response.getStrings().get(i));
            }
        }
    }
}
