import axios from "axios";
import React from "react";

// Retrieve list of texts and show them.
axios.get('/api/text')
    .then(({data}) => {
        for (var text of data) {
            console.log(text);
        }
    });

