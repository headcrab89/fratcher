import React from "react";
import {CookiesProvider} from "react-cookie";
import ReactDOM from "react-dom";

import Authentication from "./components/authentication";
import TextList from "./components/text_list";
import MatchList from "./components/match_list";

ReactDOM.render(
    <CookiesProvider>
        <div>
            <TextList />
            <MatchList />
            <Authentication/>
        </div>
    </CookiesProvider>,
    document.getElementById('root'));



