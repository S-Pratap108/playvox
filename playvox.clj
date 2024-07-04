(config
    (text-field
        :name "clientId" 
        :label "Client Id"
        :placeholder "Enter Client Id"
        :required true
    )

    (password-field
        :name "clientSecret" 
        :label "Client Secret"
        :placeholder "Enter client secret"
        :required true)

    (text-field
        :name "subDomain" 
        :label "Domain Id"
        :placeholder "Enter Domain Id"
        :required true)

    (oauth2/refresh-token-with-client-credentials
        (access-token
            (source
                (http/post
                    :url " https//{subDomain}.cloud.agyletime.io/api/authority/token"
                    (header-params
                        "Authorization" "Basic BASE_64({clientID}:{clientSecret})"
                        "grant_type" "client_credentials")
                    ))
            (fields
                access_token :<= "accessToken"
                refresh_token
                token_type :<= "tokenType"
                scope
                realm_id 
                expires_in :<= "expireIn"
            ))))
(default-source (http/get :base-url "https://{subDomain}.cloud.agyletime.io"
                    (header-params "Accept" "application/json"))
                (paging/key-based :scroll-key-query-param-name "nextPageToken"
                                :scroll-value-path-in-response "nextPageToken"
                                :limit 10)
                (auth/oauth2)
                (error-handler
                    (when :status 404 :message "Not Found")
                    (when :status 401 :action refresh)
                    (when :status 422 :message "Unprocessable entity")
                    (when :status 500 :message "Internal server error")))

(temp-entity ORGANIZATION_USER
    (api-docs-url "file:///C:/Users/SPratap/OneDrive%20-%20ADA%20Global/Desktop/Five%20Tran%20trainee%20document/CoIL%20Language%20Reference%20(V2_0_0)%20-%20Slab.pdf")
    (source (http/get :url "/organization/users")
            (setup-test
                (upon-receiving :code 200 :action (pass)))
            (extract-path "data"))
    (fields 
        id : id))
                    