$(document).ready(function () {
    const getContent = () => {
        //create url to request fragment
        let url = /content/;
        if ($('#selection').val() === "Content 1") {
            url = url + "content1";
        } else if ($('#selection').val() === "Main") {
            url = "/main";
        } else if ($('#selection').val() === "Edit") {
            url = "/edit";
        } else {
            url = url + "content2";
        }

        //load fragment and replace content
        $('#replace_div').load(url);
    }

    //call function when page is loaded
    getContent();

    //set on change listener
    $('#selection').change(getContent);
})
