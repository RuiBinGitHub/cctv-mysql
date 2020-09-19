$(document).ready(function() {
    // 获取当前语言
    var language = $("#memu1 div").text().length == 4 ? "zh" : "en";
    var tipsText1 = language == "zh" ? "數據修改成功！" : "Operating successfully!";
    var tipsText2 = language == "zh" ? "確定要删除該數據嗎？" : "Are you sure you want to delete this data?";
    var tipsText3 = language == "zh" ? "删除數據成功！" : "Operating successfully!";
    var tipsText4 = language == "zh" ? "數據上傳中..." : "Data uploading...";
    var tipsText5 = language == "zh" ? "數據修改成功！" : "Operating successfully!";
    var tipsText6 = language == "zh" ? "數據上傳異常！" : "Operating exceptioning!";
    var tipsText7 = language == "zh" ? "請檢查輸入數據！" : "Please check the input data!";
    var tipsText8 = language == "zh" ? "確定移除全部圖片？" : "Are you sure you want to remove all pictures?";
    var tipsText9 = language == "zh" ? "圖片移除成功！" : "Operating successfully!";
    var tipsText10 = language == "zh" ? "文件格式僅限於：PNG和JPG！" : "File format：PNG and JPG!";
    var tipsText11 = language == "zh" ? "圖片正在上傳中..." : "picture uploading...";
    var tipsText12 = language == "zh" ? "圖片上傳成功！" : "Operating successfully!";
    /***********************************************************************/
    var itemindex = -1;
    mikeDataList(0);
    drawPipe();   // 绘画管道
    /***********************************************************************/
    $("#value2 select:eq(0)").change(function() {
        var context = "";
        if ($(this).val() == "Kowloon") {
            context += "<option>Kowloon City District</option>";
            context += "<option>Kwun Tong District</option>";
            context += "<option>Sham Shui Po District</option>";
            context += "<option>Wong Tai Sin District</option>";
            context += "<option>Yau Tsim Mong Distric</option>";
        } else if ($(this).val() == "Hong Kong Island") {
            context += "<option>Central and Western District</option>";
            context += "<option>Eastern District</option>";
            context += "<option>Southern District</option>";
            context += "<option>Wan Chai District</option>";
        } else if ($(this).val() == "New Territories") {
            context += "<option>North District</option>";
            context += "<option>Tai Po District</option>";
            context += "<option>Islands District</option>";
            context += "<option>Kwai Tsing District</option>";
            context += "<option>Sai Kung District</option>";
            context += "<option>Sha Tin District</option>";
            context += "<option>Tsuen Wan District</option>";
            context += "<option>Tuen Mun District</option>";
            context += "<option>Yuen Long District</option>";
        }
        $("#value2 select:eq(1)").html(context);
        $("#value2 select:eq(1) option").each(function() {
            $(this).val($(this).text());
        });
        $("#value2 select:eq(1)").change();
    });
    $("#value2 select:eq(1)").change(function() {
        var context = "";
        if ($(this).val() == "North District") {
            context += "<option>Luen Wo Hui</option>";
            context += "<option>Fanling Town</option>";
            context += "<option>Cheung Wah</option>";
            context += "<option>Wah Do</option>";
            context += "<option>Wah Ming</option>";
            context += "<option>Yan Shing</option>";
            context += "<option>Fanling South</option>";
            context += "<option>Shing Fuk</option>";
            context += "<option>Ching Ho</option>";
            context += "<option>Yu Tai</option>";
            context += "<option>Sheung Shui Rural</option>";
            context += "<option>Choi Yuen</option>";
            context += "<option>Shek Wu Hui</option>";
            context += "<option>Tin Ping West</option>";
            context += "<option>Fung Tsui</option>";
            context += "<option>Sha Ta</option>";
            context += "<option>Tin Ping East</option>";
            context += "<option>Queen's Hill</option>";
        } else if ($(this).val() == "Tai Po District") {
            context += "<option>Tai po Hui</option>";
            context += "<option>Chung Ting</option>";
            context += "<option>Tai Po Central</option>";
            context += "<option>Tai Yuen</option>";
            context += "<option>Fu Heng</option>";
            context += "<option>Yee Fu</option>";
            context += "<option>Fu Ming Sun</option>";
            context += "<option>Kwong Fuk & Plover Cove</option>";
            context += "<option>Wang Fuk</option>";
            context += "<option>Tai Po Kau</option>";
            context += "<option>Wan Tan Tong</option>";
            context += "<option>San Fu</option>";
            context += "<option>Lam Tsuen Valley</option>";
            context += "<option>Po Nga</option>";
            context += "<option>Tai Wo</option>";
            context += "<option>Old Maket & Serenity</option>";
            context += "<option>Hong Lok Yuen </option>";
            context += "<option>Shuen Wan</option>";
            context += "<option>Sai Kung North</option>";
        } else if ($(this).val() == "Islands District") {
            context += "<option>Lantau</option>";
            context += "<option>Mun Yat</option>";
            context += "<option>Yat Tung Estate North</option>";
            context += "<option>Tung Chung South</option>";
            context += "<option>Tung Chung Central</option>";
            context += "<option>Tung Chung North</option>";
            context += "<option>Discovery Bay</option>";
            context += "<option>Peng Chau & Hei Ling Chau</option>";
            context += "<option>Lamma & Po Toi</option>";
            context += "<option>Cheung Chau</option>";
        } else if ($(this).val() == "Kwai Tsing District") {
            context += "<option>Kwai Hing</option>";
            context += "<option>Kwai Luen</option>";
            context += "<option>Kwai Shing East Estate</option>";
            context += "<option>Upper Tai Wo Hau</option>";
            context += "<option>Lower Tai Wo Hau</option>";
            context += "<option>Kwai Chung Estate South</option>";
            context += "<option>Kwai Chung Estate North</option>";
            context += "<option>Shek Yam</option>";
            context += "<option>Tai Pak Tin West</option>";
            context += "<option>Tai Pak Tin East</option>";
            context += "<option>On Yam</option>";
            context += "<option>Shek Lei North</option>";
            context += "<option>Shek Lei South</option>";
            context += "<option>Kwai Fong</option>";
            context += "<option>Hing Fong</option>";
            context += "<option>Wah Lai</option>";
            context += "<option>Lai Wah</option>";
            context += "<option>Cho Yiu</option>";
            context += "<option>Lai King</option>";
            context += "<option>Kwai Shing West Estate</option>";
            context += "<option>On Ho</option>";
            context += "<option>Wai Ying</option>";
            context += "<option>Tsing Yi Estate</option>";
            context += "<option>Greenfield</option>";
            context += "<option>Cheung Ching</option>";
            context += "<option>Cheung Hong</option>";
            context += "<option>Shing Hong</option>";
            context += "<option>Tsing Yi South</option>";
            context += "<option>Cheung Hang</option>";
            context += "<option>Ching Fat</option>";
            context += "<option>Cheung On</option>";
        } else if ($(this).val() == "Sai Kung District") {
            context += "<option>Sai Kung Central</option>";
            context += "<option>Pak Sha Wan</option>";
            context += "<option>Sai Kung Islands</option>";
            context += "<option>Hang Hau East</option>";
            context += "<option>Hang Hau West</option>";
            context += "<option>Choi Kin</option>";
            context += "<option>Kin Ming</option>";
            context += "<option>Do Shin</option>";
            context += "<option>Wai King</option>";
            context += "<option>Hoi Chun</option>";
            context += "<option>Po Yee</option>";
            context += "<option>Fu Kwan</option>";
            context += "<option>O Tong</option>";
            context += "<option>Sheung Tak</option>";
            context += "<option>Kwong Ming</option>";
            context += "<option>Hong King</option>";
            context += "<option>Tsui Lam</option>";
            context += "<option>Po Lam</option>";
            context += "<option>Yan Ying</option>";
            context += "<option>Wai Yan</option>";
            context += "<option>Wan Hang</option>";
            context += "<option>King Lam</option>";
            context += "<option>Hau Tak</option>";
            context += "<option>Fu Nam</option>";
            context += "<option>Tak Ming</option>";
            context += "<option>Nam On</option>";
            context += "<option>Kwan Po</option>";
            context += "<option>Wan Po North</option>";
            context += "<option>Wan Po South</option>";
        } else if ($(this).val() == "Sha Tin District") {
            context += "<option>Sha Tin Town Centre</option>";
            context += "<option>Lek Yuen</option>";
            context += "<option>Wo Che Estate</option>";
            context += "<option>City One</option>";
            context += "<option>Yue Shing</option>";
            context += "<option>Wong Uk</option>";
            context += "<option>Sha Kok</option>";
            context += "<option>Pok Hong</option>";
            context += "<option>Shui Chuen O</option>";
            context += "<option>Jat Chuen</option>";
            context += "<option>Chun Fung</option>";
            context += "<option>Sun Tin Wai</option>";
            context += "<option>Chui Tin</option>";
            context += "<option>Hin Ka</option>";
            context += "<option>Lower Shing Mun</option>";
            context += "<option>Wan Shing</option>";
            context += "<option>Keng Hau</option>";
            context += "<option>Tin Sum</option>";
            context += "<option>Chui Ka</option>";
            context += "<option>Tai Wai</option>";
            context += "<option>Chung Tin</option>";
            context += "<option>Sui Wo</option>";
            context += "<option>Fo Tan</option>";
            context += "<option>Chun Ma</option>";
            context += "<option>Hoi Nam</option>";
            context += "<option>Chung On</option>";
            context += "<option>Kam To</option>";
            context += "<option>Ma On Shan Town Centre</option>";
            context += "<option>Wu Kai Sha</option>";
            context += "<option>Lee On</option>";
            context += "<option>Fu Lung</option>";
            context += "<option>Kam Ying</option>";
            context += "<option>Yiu On</option>";
            context += "<option>Heng On</option>";
            context += "<option>Tai Shui Hang</option>";
            context += "<option>On Tai</option>";
            context += "<option>Yu Yan</option>";
            context += "<option>Di Yee</option>";
            context += "<option>Bik Woo</option>";
            context += "<option>Kwong Hong</option>";
            context += "<option>Kwong Yuen</option>";
        } else if ($(this).val() == "Tsuen Wan District") {
            context += "<option>Tak Wah</option>";
            context += "<option>Yeung Uk Road</option>";
            context += "<option>Tsuen Wan South</option>";
            context += "<option>Hoi Bun</option>";
            context += "<option>Tsuen Wan West</option>";
            context += "<option>Clague Garden</option>";
            context += "<option>Tsuen Wan Centre</option>";
            context += "<option>Discovery Park</option>";
            context += "<option>Fuk Loi</option>";
            context += "<option>Luk Yeung</option>";
            context += "<option>Ma Wan</option>";
            context += "<option>Tsuen Wan Rural</option>";
            context += "<option>Ting Sham</option>";
            context += "<option>Lai To</option>";
            context += "<option>Allway</option>";
            context += "<option>Cheung Shek</option>";
            context += "<option>Shek Wai Kok</option>";
            context += "<option>Lei Muk Shue West</option>";
            context += "<option>Lei Muk Shue East</option>";
        } else if ($(this).val() == "Tuen Mun District") {
            context += "<option>Tuen Mun Town Centre</option>";
            context += "<option>Siu Chi</option>";
            context += "<option>On Ting</option>";
            context += "<option>Siu Tsui</option>";
            context += "<option>Yau Oi South</option>";
            context += "<option>Yau Oi North</option>";
            context += "<option>Tsui Hing</option>";
            context += "<option>Shan King</option>";
            context += "<option>King Hing</option>";
            context += "<option>Hing Tsak</option>";
            context += "<option>San Hui</option>";
            context += "<option>So Kwun Wat</option>";
            context += "<option>Sam Shing</option>";
            context += "<option>Hanford</option>";
            context += "<option>Yuet Wu</option>";
            context += "<option>Siu Hei</option>";
            context += "<option>Wu King</option>";
            context += "<option>Butterfly</option>";
            context += "<option>Fu Sun</option>";
            context += "<option>Lok Tsui</option>";
            context += "<option>Lung Mun</option>";
            context += "<option>San King</option>";
            context += "<option>Leung King</option>";
            context += "<option>Tin King</option>";
            context += "<option>Po Tin</option>";
            context += "<option>Kin Sang</option>";
            context += "<option>Siu Hong</option>";
            context += "<option>Yan Tin</option>";
            context += "<option>Tuen Mun Rural</option>";
            context += "<option>Fu Tai</option>";
            context += "<option>Prime View</option>";
        } else if ($(this).val() == "Yuen Long District") {
            context += "<option>Fung Nin</option>";
            context += "<option>Yuen Long Centre</option>";
            context += "<option>Fung Cheung</option>";
            context += "<option>Yuen Lung</option>";
            context += "<option>Shap Pat Heung Central</option>";
            context += "<option>ShuiPin</option>";
            context += "<option>Nam Ping</option>";
            context += "<option>Pek Long</option>";
            context += "<option>Yuen Long Tung Tau</option>";
            context += "<option>Shap Pat Heung North</option>";
            context += "<option>Shap Pat Heung East</option>";
            context += "<option>Shap Pat Heung West</option>";
            context += "<option>Ping Shan South</option>";
            context += "<option>Hung Fuk</option>";
            context += "<option>Ha Tsuen</option>";
            context += "<option>Pilg Shan Central</option>";
            context += "<option>Shing Yan</option>";
            context += "<option>Tin Shing</option>";
            context += "<option>Tin Yau</option>";
            context += "<option>Yiu Yau</option>";
            context += "<option>Tsz Yau</option>";
            context += "<option>Kingswood South</option>";
            context += "<option>Shui Oi</option>";
            context += "<option>Shui Wah</option>";
            context += "<option>Chung Wah</option>";
            context += "<option>Chung Pak</option>";
            context += "<option>Kingswood North</option>";
            context += "<option>Yuet Yan</option>";
            context += "<option>Ching King</option>";
            context += "<option>Fu Yan</option>";
            context += "<option>Yat Chak</option>";
            context += "<option>Tin Heng</option>";
            context += "<option>Wang Yat</option>";
            context += "<option>Phg Shan North</option>";
            context += "<option>Fairview Park</option>";
            context += "<option>San Tin</option>";
            context += "<option>Kam Tin</option>";
            context += "<option>Pat Heung North</option>";
            context += "<option>Pat Heung South</option>";
        } else if ($(this).val() == "Kowloon City District") {
            context += "<option>Ma Tau Wai</option>";
            context += "<option>Sung Wong Toi</option>";
            context += "<option>Ma Hang Chung</option>";
            context += "<option>Ma Tau Kok</option>";
            context += "<option>Lok Man</option>";
            context += "<option>Sheung Lok</option>";
            context += "<option>Ho Man Tin</option>";
            context += "<option>Kadoorie</option>";
            context += "<option>Prince</option>";
            context += "<option>Kowloon Tong</option>";
            context += "<option>Lung Shing</option>";
            context += "<option>Kai Tak North</option>";
            context += "<option>Kai Tak East</option>";
            context += "<option>Kai Tak Central & South</option>";
            context += "<option>Hoi Sham</option>";
            context += "<option>To Kwa Wan North</option>";
            context += "<option>To Kwa Wan South</option>";
            context += "<option>Hok Yuen Laguna Verde</option>";
            context += "<option>Whampoa East</option>";
            context += "<option>Whampoa West</option>";
            context += "<option>Hung Hom Bay</option>";
            context += "<option>Hung Hom</option>";
            context += "<option>Ka Wai</option>";
            context += "<option>Oi Man</option>";
            context += "<option>Oi Chun</option>";
        } else if ($(this).val() == "Kwun Tong District") {
            context += "<option>Kwun Tong Central</option>";
            context += "<option>Kowloon Bay</option>";
            context += "<option>Kai Yip</option>";
            context += "<option>Lai Ching</option>";
            context += "<option>Ping Shek</option>";
            context += "<option>Choi Tak</option>";
            context += "<option>Jordan Valley</option>";
            context += "<option>Shun Tin</option>";
            context += "<option>Sheung Shun</option>";
            context += "<option>On Lee</option>";
            context += "<option>Kwun Tong On Tai</option>";
            context += "<option>Sau Mau Ping North</option>";
            context += "<option>Sau Mau Ping Central</option>";
            context += "<option>On Tat</option>";
            context += "<option>Sau Mau Ping South</option>";
            context += "<option>Po Tat</option>";
            context += "<option>Kwong Tak</option>";
            context += "<option>Hing Tin</option>";
            context += "<option>Lam Tin</option>";
            context += "<option>Ping Tin</option>";
            context += "<option>Pak Nga</option>";
            context += "<option>Chun Cheung</option>";
            context += "<option>Yau Tong East</option>";
            context += "<option>Yau Chui</option>";
            context += "<option>Yau Lai</option>";
            context += "<option>Yau Tong West</option>";
            context += "<option>Laguna City</option>";
            context += "<option>King Tin</option>";
            context += "<option>Tsui Ping</option>";
            context += "<option>Hiu Lai</option>";
            context += "<option>Po Lok</option>";
            context += "<option>Yuet Wah</option>";
            context += "<option>Hip Hong</option>";
            context += "<option>Lok Wah South</option>";
            context += "<option>Lok Wah North</option>";
            context += "<option>Hong Lok</option>";
            context += "<option>Ting On</option>";
            context += "<option>Upper Ngau Tau Kok Estate</option>";
            context += "<option>Lower Ngau Tau Kok Estate</option>";
            context += "<option>To Tai</option>";
        } else if ($(this).val() == "Sham Shui Po District") {
            context += "<option>Po Lai</option>";
            context += "<option>Cheung Sha Wan</option>";
            context += "<option>Nam Cheong North</option>";
            context += "<option>Shek Kip Mei</option>";
            context += "<option>Nam Cheong East</option>";
            context += "<option>Nam Cheong South</option>";
            context += "<option>Nam Cheong Central</option>";
            context += "<option>Nam Cheong West</option>";
            context += "<option>Fu Cheong</option>";
            context += "<option>Lai Kok</option>";
            context += "<option>Fortune</option>";
            context += "<option>Pik Wui</option>";
            context += "<option>Lai Chi Kok Central</option>";
            context += "<option>Lai Chi Kok South</option>";
            context += "<option>Mei Foo South</option>";
            context += "<option>Mei Foo Central</option>";
            context += "<option>Mei Foo North</option>";
            context += "<option>Lai Chi Kok North</option>";
            context += "<option>Un Chau</option>";
            context += "<option>So Uk</option>";
            context += "<option>Lei Cheng Uk</option>";
            context += "<option>Lung Ping & Sheung Pak Tin</option>";
            context += "<option>Ha Pak Tin</option>";
            context += "<option>Yau Yat Tsuen</option>";
            context += "<option>Nam Shan,Tai Hang Tung & Tai Hang Sai</option>";
        } else if ($(this).val() == "Wong Tai Sin District") {
            context += "<option>Lung Tsui</option>";
            context += "<option>Lung Ha</option>";
            context += "<option>Lung Sheung</option>";
            context += "<option>Fung Wong</option>";
            context += "<option>Fung Tak</option>";
            context += "<option>Lung Sing</option>";
            context += "<option>San Po Kong</option>";
            context += "<option>Tung Tau</option>";
            context += "<option>Tung Mei</option>";
            context += "<option>Lok Fu</option>";
            context += "<option>Wang Tau Hom</option>";
            context += "<option>Tin Keung</option>";
            context += "<option>Tsui Chuk & Pang Ching</option>";
            context += "<option>Chuk Yuen South</option>";
            context += "<option>Chuk Yuen North</option>";
            context += "<option>Tsz Wan West</option>";
            context += "<option>Ching Oi</option>";
            context += "<option>Ching On</option>";
            context += "<option>Tsz Wan East</option>";
            context += "<option>King Fu</option>";
            context += "<option>Choi Wan East</option>";
            context += "<option>Choi Wan South</option>";
            context += "<option>Choi Wan West</option>";
            context += "<option>Chi Choi</option>";
            context += "<option>Choi Hung</option>";
        } else if ($(this).val() == "Yau Tsim Mong Distric") {
            context += "<option>Tsim Sha Tsui West</option>";
            context += "<option>Kowloon Station</option>";
            context += "<option>Jordan West</option>";
            context += "<option>Yau Ma Tei South</option>";
            context += "<option>Charming</option>";
            context += "<option>Mong Kok West</option>";
            context += "<option>Fu Pak</option>";
            context += "<option>Olympic</option>";
            context += "<option>Cherry</option>";
            context += "<option>Tai Kok Tsui South</option>";
            context += "<option>Tai Kok Tsui North</option>";
            context += "<option>Tai Nan</option>";
            context += "<option>Mong Kok North</option>";
            context += "<option>Mong Kok East</option>";
            context += "<option>Mong Kok South</option>";
            context += "<option>Yau Ma Tei North</option>";
            context += "<option>East Tsim Sha Tsui & King's Park</option>";
            context += "<option>Jordan North</option>";
            context += "<option>Jordan South</option>";
            context += "<option>Tsim Sha Tsui Central</option>";
        } else if ($(this).val() == "Central and Western District") {
            context += "<option>Chung Wan</option>";
            context += "<option>Mid Levels East</option>";
            context += "<option>Castle Road</option>";
            context += "<option>Peak</option>";
            context += "<option>University</option>";
            context += "<option>Kwun Lung</option>";
            context += "<option>Kennedy Town & Mount Davis</option>";
            context += "<option>Sai Wan</option>";
            context += "<option>Belcher</option>";
            context += "<option>Shek Tong Tsui</option>";
            context += "<option>Sai Ying Pun</option>";
            context += "<option>Sheung Wan</option>";
            context += "<option>Tung Wah</option>";
            context += "<option>Centre Street</option>";
            context += "<option>Water Street</option>";
        } else if ($(this).val() == "Eastern District") {
            context += "<option>Tai Koo Shing West</option>";
            context += "<option>Tai Koo Shing East</option>";
            context += "<option>Lei King Wan</option>";
            context += "<option>Sai Wan Ho</option>";
            context += "<option>Adrich Bay</option>";
            context += "<option>Shaukeiwan</option>";
            context += "<option>A Kung Ngam</option>";
            context += "<option>Heng Fa Chuen</option>";
            context += "<option>Tsui Wan</option>";
            context += "<option>Yan Lam</option>";
            context += "<option>Siu Sai Wan</option>";
            context += "<option>King Yee</option>";
            context += "<option>Wan Tsui</option>";
            context += "<option>Fei Tsui</option>";
            context += "<option>Mount Parker</option>";
            context += "<option>Braemar Hill</option>";
            context += "<option>Fortress Hill</option>";
            context += "<option>Ctiy Garden</option>";
            context += "<option>Provident</option>";
            context += "<option>Fort Street</option>";
            context += "<option>Kam Ping</option>";
            context += "<option>Tanner</option>";
            context += "<option>Healthy Village</option>";
            context += "<option>Quarry Bay</option>";
            context += "<option>Nam Fung</option>";
            context += "<option>Kornhill</option>";
            context += "<option>Kornhill Garden</option>";
            context += "<option>Hing Tung</option>";
            context += "<option>Lower Yiu Tung</option>";
            context += "<option>Upper Yiu Tung</option>";
            context += "<option>Hing Man</option>";
            context += "<option>Lok Hong</option>";
            context += "<option>Tsui Tak</option>";
            context += "<option>Yue Wan</option>";
            context += "<option>Kai Hiu</option>";
        } else if ($(this).val() == "Southern District") {
            context += "<option>Aberdeen</option>";
            context += "<option>AP Lei Chau Estate</option>";
            context += "<option>AP Lei Chau North</option>";
            context += "<option>Lei Tung Ⅰ</option>";
            context += "<option>Lei Tung Ⅱ</option>";
            context += "<option>South Horizons East</option>";
            context += "<option>South Horizons West</option>";
            context += "<option>Wah Kwai</option>";
            context += "<option>Wah Fu South</option>";
            context += "<option>Wah Fu North</option>";
            context += "<option>Pokfulam</option>";
            context += "<option>Chi Fu</option>";
            context += "<option>Tin Wan</option>";
            context += "<option>Shek Yue</option>";
            context += "<option>Wong Chuk Hang</option>";
            context += "<option>Bays Area</option>";
            context += "<option>Stanley & Shek O</option>";
        } else if ($(this).val() == "Wan Chai District") {
            context += "<option>Hennessy</option>";
            context += "<option>Oi Kwan</option>";
            context += "<option>Canal Road</option>";
            context += "<option>Causeway Bay</option>";
            context += "<option>Victoria Park</option>";
            context += "<option>Tin Hau</option>";
            context += "<option>Tai Hang</option>";
            context += "<option>Jardine's Lookout</option>";
            context += "<option>Broadwood</option>";
            context += "<option>Happy Valley</option>";
            context += "<option>Stubbs Road</option>";
            context += "<option>Southorn</option>";
            context += "<option>Tai Fat Hau</option>";
        }
        $("#value2 select:eq(2)").html(context);
        $("#value2 select:eq(2) option").each(function() {
            $(this).val($(this).text());
        });
    });
    var context = "";
    context += "<option>Kowloon</option>";
    context += "<option>Hong Kong Island</option>";
    context += "<option>New Territories</option>";
    $("#value2 select:eq(0)").html(context);
    $("#value2 select:eq(0) option").each(function() {
        $(this).val($(this).text());
    });
    var dist1 = $("#value2 select:eq(0)").attr("id");
    $("#value2 select:eq(0)").val(dist1);
    $("#value2 select:eq(0)").change();
    var dist2 = $("#value2 select:eq(1)").attr("id");
    $("#value2 select:eq(1)").val(dist2);
    $("#value2 select:eq(1)").change();
    var dist3 = $("#value2 select:eq(2)").attr("id");
    $("#value2 select:eq(2)").val(dist3);
    /** ******************************************************************** */
    var id1 = $("#id1").val();  // 管道ID
    var id2 = $("#id2").val();  // 项目ID
    /** ******************************************************************** */
    $(document).scroll(function(e) {  // 是否显示菜单
        if ($(document).scrollTop() >= 180)
            $("#TitleMemu").show();
        else
            $("#TitleMemu").hide();
    });
    /** 输入坐标按钮 */
    $("#mainTitle input:eq(0)").click(function() {
    	window.open("/cctv/geominfo/showinfo?id=" + id2);
    });
    /** 下载项目按钮 */
    $("#mainTitle input:eq(1)").click(function() {
        window.open("/cctv/download?id=" + id2);
    });
    /** ******************************************************************** */
    /** 添加管道按钮 */
    $("#memu1 input:eq(0), #TitleMemu input:eq(0)").click(function() {
        var value = $("#tab1 tbody tr:last").attr("id");
        var index = value == undefined ? 0 : value;
        var length = $("#tab1 tbody tr").length;
        if (Ajax("/cctv/pipe/insert", {id: id2, no: index}))
            window.location.href = "editinfo?id=" + id2 + "&no=" + length;
    });
    /** 保存数据按钮 */
    $("#memu1 input:eq(1), #TitleMemu input:eq(1)").click(function() {
        if (!checkPipe() || !checkItem())
            return false;
        $("#tab2 tr").each(function(i) {
        	$(this).find("td:eq(0) input").attr("name", "items[" + i + "].id");
            $(this).find("td:eq(1) input").attr("name", "items[" + i + "].video");
            $(this).find("td:eq(2) input").attr("name", "items[" + i + "].photo");
            $(this).find("td:eq(3) input").attr("name", "items[" + i + "].dist");
            $(this).find("td:eq(4) input").attr("name", "items[" + i + "].cont");
            $(this).find("td:eq(5) input").attr("name", "items[" + i + "].code");
            $(this).find("td:eq(6) input").attr("name", "items[" + i + "].diam");
            $(this).find("td:eq(7) input").attr("name", "items[" + i + "].clockAt");
            $(this).find("td:eq(8) input").attr("name", "items[" + i + "].clockTo");
            $(this).find("td:eq(9) input").attr("name", "items[" + i + "].percent");
            $(this).find("td:eq(10) input").attr("name", "items[" + i + "].lengths");
            $(this).find("td:eq(11) input").attr("name", "items[" + i + "].remarks");
            $(this).find("td:eq(12) input").attr("name", "items[" + i + "].picture");
        });
        $("#memu1 input:eq(1),#TitleMemu input:eq(1)").css("background-color", "#ccc");
        $("#memu1 input:eq(1),#TitleMemu input:eq(1)").attr("disabled", true);
        if (Ajax("/cctv/pipe/commit", $("#form1").serialize()))
            showTips(tipsText1);
        setTimeout("location.reload()", 2000);
    });
    /** ******************************************************************** */
    /** 管道列表初始化事件 */
    var on = $("#no").val() == "" ? 0 : $("#no").val();
    var index = on >= $("#tab1 tr").length ? $("#tab1 tr").length - 1 : on;
    $("#tab1 tr").eq(index).find("td:eq(0)").css("background-color", "#FFD58D");
    $("#tab1 tr").eq(index).find("td:eq(0)").text("▶");
    // 管理列表初始化事件
    $("#tab1 tr").each(function(no) {
        var id = $(this).attr("id");
        // 设置管道列表按钮颜色
        $(this).find("input:eq(0)").css("background-color", "#ff8000");
        $(this).find("input:eq(1)").css("background-color", "#ee4200");
        $(this).find("input:eq(0)").click(function() {
            window.location.href = "editinfo?id=" + id2 + "&no=" + no;
        });
        $(this).find("input:eq(1)").click(function() {
            if (!confirm(tipsText2))
                return false;
            $(this).css("background-color", "#ccc");
            $(this).attr("disabled", true);
            if (Ajax("/cctv/pipe/delete", {id: id}))
                showTips(tipsText3);
            setTimeout("location.reload()", 2000);
        });
    });
    $("#tab1 tr").mouseenter(function() {  /** 鼠标靠近事件 */
        $(this).find("input").show();
    });
    $("#tab1 tr").mouseleave(function() {  /** 鼠标远离事件 */
        $(this).find("input").hide();
    });
    /** 列表滾動至當前數據 */
    $("#showpipe").scrollTop(index * 24);
    /** ******************************************************************** */
    $("#link1").attr("target", "_bank");
    $("#link1").attr("href", "http://www1.slope.landsd.gov.hk/smris/map");
    $("input[name=time]").attr("placeholder", "HH:MM");
    $("#link2").click(function() {
        $("input[name=xfile]").click();
    });
    $("input[name=xfile]").attr("title", "导入沙井深度");
    $("input[name=xfile]").change(function() {
        if (this.files.length == 0)
            return false;
        $("#form2").attr("action", "importdepth");
        $("#form2").submit();
    });
    /** ******************************************************************** */
    var reg = /^[+]{0,1}(\d+)$/; // 正整数正则表达式
    var timeReg = /^(([0-1][0-9])|2[0-3]):([0-5][0-9])$/; // 时间正则表达式
    $("#value1 input:eq(2)").css("background-color", "#EBEBE4");
    /** 數據第壹行第四行必須輸入 */
    $("#value1 input, #value4 input").on("input", function() {
        if ($(this).val() == "")
            $(this).css("background-color", "#f00");
        else
            $(this).css("background-color", "#fff");
    });
    // Pipe Length Reference
    $("#value1 input:eq(2)").on("input", function() {
        if ($(this).val() == "")
            $(this).css("background-color", "#FF0000");
        else
            $(this).css("background-color", "#EBEBE4");
    });
    // Survey ID和Year Laid只能輸入數字
    $("input[name=no],input[name=yearlaid]").on("input", function() {
        if ($(this).val() == '' || !reg.test($(this).val()))
            $(this).css("background-color", "#f00");
        else
            $(this).css("background-color", "#fff");
    });
    $("input[name=no],input[name=yearlaid]").keypress(function(event) {
        if (event.which >= 48 && event.which <= 57)
            return true;
        return false;
    });
    
    // 是否斜坡修改事件
    $("select[name=slope]").change(function() {
        if ($(this).val() == "N") {
            $("input[name=sloperef]").css("background-color", "#fff");
            $("input[name=sloperef]").val("N/A");
        } else {
            $("input[name=sloperef]").css("background-color", "#f00");
            $("input[name=sloperef]").val("");
        }
    });
    
    // 啟用日期控件
    $("input[name=date]").attr("readonly", true);
    $("input[name=date]").bind("focus click", function() {
    	$(this).css("background-color", "#fff");
        laydate();
    });
    
    // 限制時間輸入格式
    $("input[name=time]").bind("input", function(event) {
        if ($(this).val().length == 2)
            $(this).val($(this).val() + ":");
        if (timeReg.test($(this).val()))
            $(this).css("background-color", "#fff");
        else
            $(this).css("background-color", "#f00");
    });
    
    // Start MH
    $("input[name=smanholeno]").attr("title", "*建議字符長度<10，長度過長可能會造成數據丟失！");
    $("input[name=smanholeno]").on("input", function() {
        $(this).val($(this).val().toUpperCase());
        var value = $(this).val();
        if (value == null || value == "")
            $(this).css("background-color", "#f00");
        else
            $(this).css("background-color", "#fff");
        if ($("select[name=dire]").val() == "D" && value != "") {
            $("input[name=reference]").css("background-color", "#EBEBE4");
            $("input[name=reference]").val(value + "X");
        }
        if ($("select[name=dire]").val() == "D" && value == "") {
            $("input[name=reference]").css("background-color", "#FF0000");
            $("input[name=reference]").val("");
        }
        $("#tab2 tbody tr").each(function() {
        	var dist = $(this).find("td:eq(3) input").val();
        	var code = $(this).find("td:eq(5) input").val();
        	if (dist == 0.0 && code == "MH")
        		$(this).find("td:eq(11) input").val(value);
        });
    });
    
    // Finish MH
    $("input[name=fmanholeno]").attr("title", "*建議字符長度<10，長度過長可能會造成數據丟失！");
    $("input[name=fmanholeno]").on("input", function() {
    	$(this).val($(this).val().toUpperCase());
        var value = $(this).val();
        if (value == null || value == "")
            $(this).css("background-color", "#f00");
        else
            $(this).css("background-color", "#fff");
        if ($("select[name=dire]").val() == "U" && value != "") {
        	$("input[name=reference]").css("background-color", "#EBEBE4");
        	$("input[name=reference]").val(value + "X");
        }
        if ($("select[name=dire]").val() == "U" && value == "") {
        	$("input[name=reference]").css("background-color", "#FF0000");
        	$("input[name=reference]").val("");
        }
        var len = $("input[name=testlength]").val();
        $("#tab2 tbody tr").each(function() {
        	var dist = $(this).find("td:eq(3) input").val();
        	var code = $(this).find("td:eq(5) input").val();
        	if (dist == len && code == "MH")
        		$(this).find("td:eq(11) input").val(value);
        });
    });
    
    // 流向改變事件
    $("select[name=dire]").change(function() {
        if ($(this).val() == "D")
        	$("input[name=smanholeno]").trigger("input");
        if ($(this).val() == "U")
        	$("input[name=fmanholeno]").trigger("input");
    });

    // Size(Dia)H
    $("input[name=hsize]").on("input", function() {
        if ($(this).val() == "" || isNaN($(this).val()))
            $(this).css("background-color", "#f00");
        else
            $(this).css("background-color", "#fff");
    });
    // Size(Dia)W
    $("input[name=wsize]").on("input", function() {
        if (isNaN($(this).val()))
            $(this).css("background-color", "#f00");
        else
            $(this).css("background-color", "#fff");
    });
    $("input[name=hsize],input[name=wsize]").keypress(function(event) {
        if (event.which >= 48 && event.which <= 57 || event.which == 46)
            return true;
        return false;
    });
    
    // Pipe Length和Total Length
    $("input[name=pipelength],input[name=testlength]").on("input", function() {
        if ($(this).val() == "" || isNaN($(this).val()))
            $(this).css("background-color", "#f00");
        else
            $(this).css("background-color", "#fff");
    });
    $("input[name=pipelength],input[name=testlength]").keypress(function(event) {
        if (event.which >= 48 && event.which <= 57 || event.which == 46)
            return true;
        return false;
    });

    // Invert Level=Cover Level-Start Depth
    $("input[name=sdepth],input[name=scoverlevel]").attr("title", "*不確定具體數值時請輸入：--");
    $("input[name=sdepth],input[name=scoverlevel]").on("input", function() {
        if ($(this).val() != "" && (!isNaN($(this).val()) || $(this).val() == "--"))
            $(this).css("background-color", "#fff");
        else
            $(this).css("background-color", "#f00");
        var num1 = $("input[name=sdepth]").val();
        var num2 = $("input[name=scoverlevel]").val();
        if (!isNaN(num1) && !isNaN(num2)) {
        	$("input[name=sinvertlevel]").css("background-color", "#EBEBE4");
            $("input[name=sinvertlevel]").val((num2 - num1).toFixed(2));
        }
        if (num1 == "" || num2 == "" || num1 == "--" || num2 == "--") {
        	$("input[name=sinvertlevel]").css("background-color", "#EBEBE4");
            $("input[name=sinvertlevel]").val("--");
        }
    });
    
    // Invert Level=Cover Level-Start Depth
    $("input[name=fdepth],input[name=fcoverlevel]").attr("title", "*不確定具體數值時請輸入：--");
    $("input[name=fdepth],input[name=fcoverlevel]").on("input", function() {
        if ($(this).val() != "" && (!isNaN($(this).val()) || $(this).val() == "--"))
            $(this).css("background-color", "#fff");
        else
            $(this).css("background-color", "#f00");
        var num1 = $("input[name=fdepth]").val();
        var num2 = $("input[name=fcoverlevel]").val();
        if (!isNaN(num1) && !isNaN(num2)) {
        	$("input[name=finvertlevel]").css("background-color", "#EBEBE4");
            $("input[name=finvertlevel]").val((num2 - num1).toFixed(2));
        }
        if (num1 == "" || num2 == "" || num1 == "--" || num2 == "--") {
        	$("input[name=finvertlevel]").css("background-color", "#EBEBE4");
            $("input[name=finvertlevel]").val("--");
        }
    });
    $("input[name=sinvertlevel],input[name=finvertlevel]").attr("readonly", true);
    $("input[name=sinvertlevel],input[name=finvertlevel]").attr("tabIndex", -1);
    /** ******************************************************************** */
    function checkPipe() {
        var result = true;
        // 第一行
        var value = $("input[name=no]").val();
        if (value == "" || !reg.test(value)) {
        	$("input[name=no]").css("background-color", "#f00");
        	result = false;
        }
        if ($("input[name=workorder]").val() == "") {
        	$("input[name=workorder]").css("background-color", "#f00");
        	result = false;
        }
        if ($("input[name=reference]").val() == "") {
        	$("input[name=reference]").css("background-color", "#f00");
        	result = false;
        }
        if ($("input[name=purposes]").val() == "") {
        	$("input[name=purposes]").css("background-color", "#f00");
        	result = false;
        }
        if ($("input[name=sloperef]").val() == "") {
        	$("input[name=sloperef]").css("background-color", "#f00");
        	result = false;
        }
	    var value = $("input[name=yearlaid]").val();
	    if (value == "" || !reg.test(value)) {
	    	$("input[name=yearlaid]").css("background-color", "#f00");
	    	result = false;
	    }
	    if ($("input[name=date]").val() == "") {
        	$("input[name=date]").css("background-color", "#f00");
        	result = false;
        }
	    var value = $("input[name=time]").val();
        if (value == "" || !timeReg.test(value)) {
        	$("input[name=time]").css("background-color", "#f00");
            result = false;
        }
        // 第三行
        if ($("input[name=smanholeno]").val() == "") {
        	$("input[name=smanholeno]").css("background-color", "#f00");
        	result = false;
        }
        if ($("input[name=smanholeno]").val() == "") {
        	$("input[name=fmanholeno]").css("background-color", "#f00");
        	result = false;
        }
        var value = $("input[name=hsize]").val();
        if (value == "" || isNaN(value)) {
        	$("input[name=hsize]").css("background-color", "#f00");
        	result = false;
        }
        var value = $("input[name=pipelength]").val();
        if (value == "" || isNaN(value)) {
        	$("input[name=pipelength]").css("background-color", "#f00");
        	result = false;
        }
        var value = $("input[name=testlength]").val();
        if (value == "" || isNaN(value)) {
        	$("input[name=testlength]").css("background-color", "#f00");
        	result = false;
        }
        // 第四行
        var value = $("input[name=sdepth]").val();
        if (value == "" || (isNaN(value) && value != "--")) {
        	$("input[name=sdepth]").css("background-color", "#f00");
        	result = false;
        }
        var value = $("input[name=scoverlevel]").val();
        if (value == "" || (isNaN(value) && value != "--")) {
        	$("input[name=scoverlevel]").css("background-color", "#f00");
        	result = false;
        }
        var value = $("input[name=fdepth]").val();
        if (value == "" || (isNaN(value) && value != "--")) {
        	$("input[name=fdepth]").css("background-color", "#f00");
        	result = false;
        }
        var value = $("input[name=fcoverlevel]").val();
        if (value == "" || (isNaN(value) && value != "--")) {
        	$("input[name=fcoverlevel]").css("background-color", "#f00");
        	result = false;
        }
        
        if ($("input[name=category]").val() == "") {
        	$("input[name=category]").css("background-color", "#f00");
        	result = false;
        }
        if ($("input[name=videono]").val() == "") {
        	$("input[name=videono]").css("background-color", "#f00");
        	result = false;
        }
        if (result == false)
            showTips(tipsText7);
        return result;
    }
    /** ******************************************************************** */
    /** *********************** 管道数据输入检测完成 *********************** */
    /** ******************************************************************** */
    // 视频播放器点击事件
    $("#video").click(function() {
    	var src = $(this).attr("src");
        if (src != undefined && src != "")
            this.paused ? this.play() : this.pause();
        $(this).focus();
    });
    // 视频播放器双击事件
    $("#video").dblclick(function() {
        $("#file1").click();
    });
    
    // 图片预览框双击事件
    $("#pic1").dblclick(function() {
        $("#file2").click();
    });
    
    // 设置截频保存快捷键
    $(document, "#video").keydown(function(e) {
        if (e.altKey && e.which == 65) // 截屏
        	keydownAlt_A();
        if (e.altKey && e.which == 83) // 保存
        	keydownAlt_S();
    });
    // 截取视频
    function keydownAlt_A() {
    	if ($("#video").attr("src") == undefined)
            return false;
        var video = $("#video")[0];
        var canvas1 = $("#canvas1")[0];
        var context = canvas1.getContext("2d");
        $(canvas1).attr("width", video.videoWidth);
        $(canvas1).attr("height", video.videoHeight);
        context.drawImage(video, 0, 0);
        $("#pic1").attr("src", canvas1.toDataURL("image/png"));
    }
    // 保存图片
    function keydownAlt_S() {
    	if (itemindex == -1 || $("#pic1").attr("src") == undefined)
            return false;
        var canvas1 = $("#canvas1")[0];
        var imgData = canvas1.toDataURL("image/png");
        $("#pic2").attr("src", $("#pic1").attr("src"));
        $("#tab2 tr").eq(itemindex).find("td:eq(2) input").val("#待保存#");
        $("#tab2 tr").eq(itemindex).find("td:eq(12) input").val(imgData);
    }
    /** ****************************************************************************** */
    // 快捷键控制视频播放
    $(document, "#video").keydown(function(e) {
        var video = $("#video")[0];
        if (e.ctrlKey && e.which == 37)
            video.currentTime = video.currentTime - 0.03;
        if (e.ctrlKey && e.which == 39)
            video.currentTime = video.currentTime + 0.03;
    });
    /** ****************************************************************************** */
    /** 視頻文件選擇器 */
    $("#file1").change(function() {
    	if (this.files.length == 0)
            return false;
        var url = getURL(this.files[0]);
        $("#video").attr("src", url);
        $("#video").attr("poster", "");
        var name = this.files[0].name;
        var text = name.substring(0, name.lastIndexOf("."));
        $("input[name=videono]").val(text);
        this.value = "";
    });
    /** 圖片文件選擇器 */
    $("#file2").change(function() {
    	if (this.files.length == 0)
            return false;
        var url = getURL(this.files[0]);
        $("#pic1").attr("src", url);
        var img = new Image();
        img.src = $("#pic1").attr("src");
        img.onload = function() {
            var canvas1 = $("#canvas1")[0];
            $(canvas1).attr("width", img.width);
            $(canvas1).attr("height", img.height);
            var context = canvas1.getContext("2d");
            context.drawImage($("#pic1")[0], 0, 0);
            this.value = "";
        };
    });
    /** 根據文件獲取路徑 */
    function getURL(file) {
        var url = null;
        if (window.createObjectURL != undefined)
            url = window.createObjectURL(file);
        else if (window.URL != undefined)
            url = window.URL.createObjectURL(file);
        else if (window.webkitURL != undefined)
            url = window.webkitURL.createObjectURL(file);
        return url;
    }
    /** ******************************************************************** */
    // 添加记录
    $("#memu2 input[type=button]:eq(0)").click(function() {
        $("#tab2").append(getContext());
        var length = $("#tab2 tr").length - 1;
        initItemList(length);
    });
    // 插入记录
    $("#memu2 input[type=button]:eq(1)").click(function() {
        if (itemindex == -1)
            return false;
        $("#tab2 tr").eq(itemindex).before(getContext());
        initItemList(itemindex);
    });
    // 删除记录
    $("#memu2 input[type=button]:eq(2)").click(function() {
        if (itemindex == -1 || !confirm(tipsText2))
            return false;
        var id = $("#tab2 tr").eq(itemindex).find("[type=hidden]").val();
        if (id != "" && id != "0")
            Ajax("/cctv/item/delete", {id: id});
        $("#tab2 tr").eq(itemindex).remove();
        initItemList(itemindex);
        var list = $("#tab2 tbody tr").map(function() {
        	return $(this).find("td:eq(3) input").val();
        }).get();
        var max = Math.max.apply(null, list);
        var length = max == "-Infinity" ? 0.0 : max;
        $("input[name=testlength]").val(length);
    });
    
    // 导入图片
    $("#memu2 input[type=button]:eq(3)").click(function() {
        $("input[name=files]").click();
    });
    // 删除图片
    $("#memu2 input[type=button]:eq(4)").click(function() {
        if (!confirm(tipsText8))
        	return false;
        if (Ajax("/cctv/item/removeimage", {id: id2}))
            showTips(tipsText9);
        setTimeout("location.reload()", 2000);
    });
    $("input[name=files]").attr("webkitdirectory", true);
    $("input[name=files]").change(function() {
        if (this.files.length == 0)
            return false;
        for (var i = 0; i < this.files.length; i++) {
            var name = this.files[i].name;
            var loca = name.lastIndexOf(".");
            var type = name.substr(loca + 1).toUpperCase();
            if (type != "PNG" && type != "JPG") {
                showTips(tipsText10);
                $(this).val("");
                return false;
            }
        }
    	$("#form3").attr("action", "/cctv/item/importimages");
    	$("#form3").submit();
    });
    /***********************************************************************/
    /** 獲取Context */
    function getContext() {
        var context = "<tr>";
        context += "  <td width='3%'><input type='hidden' value='0'/><a></a></td>";
        context += "  <td width='10%'><input type='text'/></td>";
        context += "  <td width='10%'><input type='text'/><img/></td>";
        context += "  <td width='10%'><input type='text'/></td>";
        context += "  <td width='8%'><input type='text'/></td>";
        context += "  <td width='8%'><input type='text'/></td>";
        context += "  <td width='10%'><input type='text'/></td>";
        context += "  <td width='5%'><input type='text'/></td>";
        context += "  <td width='5%'><input type='text'/></td>";
        context += "  <td width='5%'><input type='text'/></td>";
        context += "  <td width='5%'><input type='text'/></td>";
        context += "  <td><input type='text'/></td>";
        context += "  <td><input type='text'/></td>";
        context += "</tr>";
        return context;
    }
    /**************************************************************************/
    var codelist1 = new Array();
    //mm
    codelist1.push("DI", "ST");
    /**************************************************************************/
    var codelist2 = new Array();
    //%
    codelist2.push("DEC", "DE C", "DECJ", "DER", "DE R", "DES", "DE S", "DESJ", "WL", "WLC", "WL C", "WLT", "WL T", "DH", "D H", "DV", "D V");
    /**************************************************************************/
    var codelist3 = new Array();
    //clock1
    codelist3.push("CL", "C L", "CLJ", "EX", "EXJ", "FIC", "FIT", "FWCS", "FWTF", "FL", "F L", "FLJ", "LWL", "LX WL", "WXL", "WX L");
    /**************************************************************************/
    var codelist4 = new Array();
    //clock2
    codelist4.push("B", "BJ", "CC", "C C", "CCJ", "CM", "C M", "CMJ", "CS", "C S", "CSJ", "FC", "F C", "FCJ", "FMJ", "FS", "F S", "FSJ", "JDL", "JD (L)", "JDM", "JD (M)", "MB", "ML", "MM", "MS");
    codelist4.push("SS-1", "SS-2", "SS-3", "SSL", "SSM", "SSS", "SW-1", "SW-2", "SW-3", "SWL", "SWM", "SWS", "LWC", "LX WC", "LWS", "LXWM", "LWM", "LXB", "LX B", "LXC", "LX C", "LXD", "LX D");
    codelist4.push("RPH", "RP H", "RPI", "RP I", "RPL", "RP L", "RPP", "RPR", "RP R", "RXM", "RX M", "WXC", "WX C", "WXS", "WX S", "LXE", "LX E");
    /**************************************************************************/
    var codelist5 = new Array();
    //remark
    codelist5.push("LC", "REM", "REM", "SC", "VVR", "VZ", "V Z", "GO", "LVS", "CU S", "LVW", "CU W", "PC", "P C", "#4-1", "#4-2", "#4-3", "#4-4", "#4-5", "SAA", "SAC", "SAZ", "MC");
    codelist5.push("CP", "CPF", "GY", "GYF", "IC", "ICF", "LH", "LHF", "BR", "BRF", "MH", "MHF", "OS", "OSF", "CZ", "OCF", "OF", "OFF", "RE", "REF", "SK", "SKF");
    codelist5.push("C P", "G Y", "I C", "L H", "B R", "O S", "O C", "O F", "R E");
    /**************************************************************************/
    var codelist6 = new Array();
    //mm  clock1
    codelist6.push("CX", "CN", "C N", "CNJ", "CXD", "CX D", "CXDJ", "CXP", "CX P", "CXPJ", "JN", "JNJ", "JXB", "JX B", "JXBJ", "JXD", "JX D", "JXDJ", "JXP", "JX P", "JXPJ");
    /**************************************************************************/
    var codelist8 = new Array();
    //%  clock2
    codelist8.push("RM", "R M", "RMJ", "DAF", "DE F", "DAFJ", "DAG", "DE G", "DAGJ", "DAZ", "DE Z", "DAZJ", "EH", "DE E-1", "DE E-2", "DE E-3", "EHJ", "EL", "ELJ", "EM", "EMJ", "ESH", "ESL", "ESM");
    /**************************************************************************/
    var codelist9 = new Array();
    //%  remark
    codelist9.push("DEZ", "DE X", "DEZJ", "DZ");
    /**************************************************************************/
    var codelist10 = new Array();
    //clock1  clock2
    codelist10.push("DB", "HA", "H", "HAJ", "HB", "HBJ", "SRB", "SR B", "SRI", "SR", "SV", "SVJ", "VV", "VVJ");
    /**************************************************************************/
    var codelist11 = new Array();
    //clock2  remark
    codelist11.push("RF", "R F", "RFJ", "FM", "F M", "LXZ", "LX Z", "RXZ", "RX Z", "RPS", "RP S", "RPZ", "RP Z");
    /**************************************************************************/
    var codelist12 = new Array();
    //mm  %  clock1
    codelist12.push("CXI", "CX I", "CXIJ");
    /**************************************************************************/
    var codelist13 = new Array();
    //mm  clock1  remark
    codelist13.push("CXB", "CX B", "CXBJ", "CXZ", "CX Z", "CXZJ", "JXZ", "JX Z", "JXZJ");
    /**************************************************************************/
    var codelist14 = new Array();
    //%  clock1 clock2
    codelist14.push("OBB", "OB B", "OBC", "OB C", "OBI", "OB I", "OBM", "OB M", "OBP", "OB P", "OBS", "OB S");
    /**************************************************************************/
    var codelist15 = new Array();
    //%  clock2  remark
    codelist15.push("DAZ", "DA Z", "DAZJ");
    /**************************************************************************/
    var codelist16 = new Array();
    //clock1  clock2  remark
    codelist16.push("ID", "I D", "IDJ", "IG", "I G", "IGJ", "IR", "I R", "IRJ", "IS", "I S", "ISJ", "SRZ", "S O");
    /**************************************************************************/
    var codelist17 = new Array();
    //mm  %  clock1  clock2
    codelist17.push("INF", "ING F", "INFJ", "ING", "ING G", "INGJ", "INP", "INPJ", "INS", "ING S", "INSJ");
    /**************************************************************************/
    var codelist18 = new Array();
    //%  clock1  clock2  remark
    codelist18.push("OBZ", "OB Z");
    /**************************************************************************/
    var codelist19 = new Array();
    //mm  %  clock1  clock2  remark
    codelist19.push("INZ", "ING Z", "INZJ");
    /**************************************************************************/
    var contlist = new Array();
    $("#conts option").each(function() {
        contlist.push($(this).val());
    });
    var codelist = new Array();
    $("#codes option").each(function(i) {
        codelist.push($(this).val());
    });
    // 行点击事件
    var path = $("#path").val();
    $("#tab2").on("click", "tr", function() {
    	itemindex = $(this).parent().find("tr").index($(this));
    	$("#tab2 tbody tr").find("td:eq(0) a").text("");
        $(this).find("td:eq(0) a").text("▶");
        var value = $(this).find("input:last").val();
        if (value == "" || value.length == 0)
        	$("#pic2").attr("src", "/cctv/img/blank.png");
        else if (value != "" && value.length < 40)
            $("#pic2").attr("src", path + value + ".png");
        else if (value != "" && value.length > 40)
        	$("#pic2")[0].src = value;
    });
    // 单元格获取焦点事件
    $("#tab2").on("focus", "tr td, tr td input", function(event) {
    	event.stopPropagation();
    	$(this).parents("tr").click();
        $(this).select();
    });
    // 单元格键盘按下事件
    $("#tab2").on("keydown", "tr td", function(event) {
    	var low = $(this).parents("tr").index();
    	var row = $(this).index();
    	if (event.keyCode == 9) {
            $(this).focus();
        } else if (event.keyCode == 37) {
            $(this).prev().focus();
        } else if (event.keyCode == 39) {
            $(this).next().focus();
        } else if (event.keyCode == 38 && low != 0) {
            $("#tab2 tbody tr").eq(low - 1).find("td").eq(row).focus();
            return false;
        } else if (event.keyCode == 40) {
            if (low == $("#tab2 tbody tr").length - 1)
                $("#memu2 input[type=button]:eq(0)").click();
            $("#tab2 tbody tr").eq(low + 1).find("td").eq(row).focus();
            return false;
        } else
            $(this).find("input").focus();
    	 if (event.altKey && event.which == 65)
         	keydownAlt_A();
         if (event.altKey && event.which == 83)
         	keydownAlt_S();
    });
    // 输入框键盘按下事件
    $("#tab2").on("keydown", "tr td input", function(event) {
    	event.stopPropagation();
    	var low = $(this).parents("tr").index();
    	var row = $(this).parents("td").index();
        if (event.keyCode == 9) {
            $(this).parent().focus();
        } else if (event.keyCode == 37 && $(this).val() == "") {
        	$(this).parent().prev().focus();
        } else if (event.keyCode == 39 && $(this).val() == "") {
        	$(this).parent().next().focus();
        } else if (event.keyCode == 38 && low > 0) {
        	$("#tab2 tbody tr").eq(low - 1).find("td").eq(row).focus();
            return false;
        } else if (event.keyCode == 40 || event.keyCode == 13) {
            if (low == $("#tab2 tbody tr").length - 1)
                $("#memu2 input[type=button]:eq(0)").click();
            $("#tab2 tbody tr").eq(low + 1).find("td").eq(row).focus();
            return false;
        }
        if (event.altKey && event.which == 65)
        	keydownAlt_A();
        if (event.altKey && event.which == 83)
        	keydownAlt_S();
    });
    // 第三个单元格Photo No
    $("#tab2").on("mouseenter", "tr td:nth-child(3)", function(event) {
    	var value = $(this).find("input").val();
        if (value == "" || value == "#已移除#") {
            $(this).find("img").attr("src", "/cctv/img/append.png");
            $(this).find("img").click(function() {
                $(this).prev().val("*插入图片")
            });
        } else if (value == "*插入图片") {
            $(this).find("img").attr("src", "/cctv/img/remove.png");
            $(this).find("img").click(function() {
                $(this).prev().val("");
            });
        } else if (value == "#待保存#") {
            $(this).find("img").attr("src", "/cctv/img/remove.png");
            $(this).find("img").click(function() {
	            $(this).prev().val("");
                $(this).parents("tr").find("td:eq(12) input").val("");
            });
        } else {
            $(this).find("img").attr("src", "/cctv/img/remove.png");
            $(this).find("img").click(function() {
                $(this).prev().val("#已移除#");
                $(this).parents("tr").find("td:eq(12) input").val("");
            });
        }
        $(this).find("img").show();
    });
    $("#tab2").on("mouseleave", "tr td:nth-child(3)", function(event) {
    	$(this).find("img").hide();
    });
    // 第四个单元格Distance
    $("#tab2").on("input", "tr td:nth-child(4) input", function(event) {
    	if ($(this).val() == "" || isNaN($(this).val())) {
            $(this).css("background-color", "#f00");
            return false;
    	}
        $(this).css("background-color", "#fff");
        var list = $("#tab2 tbody tr").map(function() {
        	return $(this).find("td:eq(3) input").val();
        }).get();
        var max = Math.max.apply(null, list);
        var value = max == "-Infinity" ? 0.0 : max;
        $("input[name=testlength]").val(value);
        $("#tab2 tbody tr").each(function(i) {
        	var color1 = $(this).find("td:eq(3) input").css("background-color");
        	var color2 = $(this).find("td:eq(5) input").css("background-color");
            if (color1 != null && color1 == "rgb(255, 255, 0)")
            	$(this).find("td:eq(3) input").css("background-color", "#fff");
            if (color2 != null && color2 == "rgb(255, 255, 0)")
            	$(this).find("td:eq(5) input").css("background-color", "#fff");
        });
    });
    // 第五个单元格Cont Def
    $("#tab2").on("input", "tr td:nth-child(5) input", function(event) {
    	if ($(this).val() != "" && contlist.indexOf($(this).val()) == -1)
            $(this).css("background-color", "#f00");
        else
            $(this).css("background-color", "#fff");
    });
    // 第六个单元格Code
    var space = new RegExp(" ", "g");
    $("#tab2").on("input", "tr td:nth-child(6) input", function(event) {
    	$(this).val($(this).val().toUpperCase());
    	if ($(this).val() == "")
    		$(this).attr("list", "codes");
    	else {
    		var value = $(this).val().replace(space, "\\ ");
    		$("#icode").html($("#codes option[value^=" + value + "]").clone());
    		$(this).attr("list", "icode");
    	}
    	$(this).parents("tr").find("td:eq(11) input").attr("list", null);
        $(this).parents("tr").find("td:eq(8) input").css("background-color", "#FFFFFF");
        $(this).parents("tr").find("td:eq(9) input").css("background-color", "#FFFFFF");
        $(this).parents("tr").find("td:eq(10) input").css("background-color", "#FFFFFF");
        $(this).parents("tr").find("td:eq(11) input").css("background-color", "#FFFFFF");
        if (codelist.indexOf($(this).val()) == -1)
            $(this).css("background-color", "#FF0000");
        else {
            $(this).css("background-color", "#FFFFFF");
            $(this).parents("tr").find("td:eq(11) input").attr("list", $(this).val());
            var dist = $(this).parents("tr").find("td:eq(3) input").val();
            $("#tab2 tbody tr").each(function(i) {
            	var color1 = $(this).find("td:eq(3) input").css("background-color");
            	var color2 = $(this).find("td:eq(5) input").css("background-color");
                if (color1 != null && color1 == "rgb(255, 255, 0)")
                	$(this).find("td:eq(3) input").css("background-color", "#fff");
                if (color2 != null && color2 == "rgb(255, 255, 0)")
                	$(this).find("td:eq(5) input").css("background-color", "#fff");
            });
            // 设置颜色提醒
            if (codelist1.indexOf($(this).val()) != -1) {
            	$(this).parents("tr").find("td:eq(10) input").css("background-color", "#FFFF00");
            } else if (codelist2.indexOf($(this).val()) != -1) {
            	$(this).parents("tr").find("td:eq(9) input").css("background-color", "#FFFF00");
            } else if (codelist3.indexOf($(this).val()) != -1) {
            	$(this).parents("tr").find("td:eq(7) input").css("background-color", "#FFFF00");
            } else if (codelist4.indexOf($(this).val()) != -1) {
            	$(this).parents("tr").find("td:eq(8) input").css("background-color", "#FFFF00");
            } else if (codelist5.indexOf($(this).val()) != -1) {
            	$(this).parents("tr").find("td:eq(11) input").css("background-color", "#FFFF00");
            } else if (codelist6.indexOf($(this).val()) != -1) {
            	$(this).parents("tr").find("td:eq(7) input").css("background-color", "#FFFF00");
            	$(this).parents("tr").find("td:eq(10) input").css("background-color", "#FFFF00");
            } else if (codelist8.indexOf($(this).val()) != -1) {
            	$(this).parents("tr").find("td:eq(8) input").css("background-color", "#FFFF00");
            	$(this).parents("tr").find("td:eq(9) input").css("background-color", "#FFFF00");
            } else if (codelist9.indexOf($(this).val()) != -1) {
            	$(this).parents("tr").find("td:eq(9) input").css("background-color", "#FFFF00");
            	$(this).parents("tr").find("td:eq(11) input").css("background-color", "#FFFF00");
            } else if (codelist10.indexOf($(this).val()) != -1) {
            	$(this).parents("tr").find("td:eq(7) input").css("background-color", "#FFFF00");
            	$(this).parents("tr").find("td:eq(8) input").css("background-color", "#FFFF00");
            } else if (codelist11.indexOf($(this).val()) != -1) {
            	$(this).parents("tr").find("td:eq(8) input").css("background-color", "#FFFF00");
            	$(this).parents("tr").find("td:eq(11) input").css("background-color", "#FFFF00");
            } else if (codelist12.indexOf($(this).val()) != -1) {
            	$(this).parents("tr").find("td:eq(7) input").css("background-color", "#FFFF00");
            	$(this).parents("tr").find("td:eq(9) input").css("background-color", "#FFFF00");
            	$(this).parents("tr").find("td:eq(10) input").css("background-color", "#FFFF00");
            } else if (codelist13.indexOf($(this).val()) != -1) {
            	$(this).parents("tr").find("td:eq(7) input").css("background-color", "#FFFF00");
            	$(this).parents("tr").find("td:eq(10) input").css("background-color", "#FFFF00");
            	$(this).parents("tr").find("td:eq(11) input").css("background-color", "#FFFF00");
            } else if (codelist14.indexOf($(this).val()) != -1) {
            	$(this).parents("tr").find("td:eq(9) input").css("background-color", "#FFFF00");
            	$(this).parents("tr").find("td:eq(7) input").css("background-color", "#FFFF00");
            	$(this).parents("tr").find("td:eq(8) input").css("background-color", "#FFFF00");
            } else if (codelist15.indexOf($(this).val()) != -1) {
            	$(this).parents("tr").find("td:eq(8) input").css("background-color", "#FFFF00");
            	$(this).parents("tr").find("td:eq(9) input").css("background-color", "#FFFF00");
            	$(this).parents("tr").find("td:eq(11) input").css("background-color", "#FFFF00");
            } else if (codelist16.indexOf($(this).val()) != -1) {
            	$(this).parents("tr").find("td:eq(7) input").css("background-color", "#FFFF00");
            	$(this).parents("tr").find("td:eq(8) input").css("background-color", "#FFFF00");
            	$(this).parents("tr").find("td:eq(11) input").css("background-color", "#FFFF00");
            } else if (codelist17.indexOf($(this).val()) != -1) {
                $(this).parents("tr").find("td:eq(9) input").css("background-color", "#FFFF00");
                $(this).parents("tr").find("td:eq(10) input").css("background-color", "#FFFF00");
                $(this).parents("tr").find("td:eq(7) input").css("background-color", "#FFFF00");
                $(this).parents("tr").find("td:eq(8) input").css("background-color", "#FFFF00");
            } else if (codelist18.indexOf($(this).val()) != -1) {
                $(this).parents("tr").find("td:eq(9) input").css("background-color", "#FFFF00");
                $(this).parents("tr").find("td:eq(7) input").css("background-color", "#FFFF00");
                $(this).parents("tr").find("td:eq(8) input").css("background-color", "#FFFF00");
                $(this).parents("tr").find("td:eq(11) input").css("background-color", "#FFFF00");
            } else if (codelist19.indexOf($(this).val()) != -1) {
                $(this).parents("tr").find("td:eq(7) input").css("background-color", "#FFFF00");
                $(this).parents("tr").find("td:eq(8) input").css("background-color", "#FFFF00");
                $(this).parents("tr").find("td:eq(9) input").css("background-color", "#FFFF00");
                $(this).parents("tr").find("td:eq(10) input").css("background-color", "#FFFF00");
                $(this).parents("tr").find("td:eq(11) input").css("background-color", "#FFFF00");
            }
        }
        if ($("#tab2 tbody tr").eq(itemindex).find("td:eq(11) input").val() != "")
            return false;
        var dist = $(this).parents("tr").find("td:eq(3) input").val();
        var length = $("input[name=testlength]").val();
        if ($(this).val() == "MH" && Number(dist) == 0.0) {
            var smh = $("input[name=smanholeno]").val();
            $(this).parents("tr").find("td:eq(11) input").val(smh);
        } else if ($(this).val() == "MH" && Number(dist) == length) {
            var fmh = $("input[name=fmanholeno]").val();
            $(this).parents("tr").find("td:eq(11) input").val(fmh);
        }
    });
 // 第八个单元格
    $("#tab2").on("input", "tr td:nth-child(8) input", function (event) {
        const reg = /^(0[1-9]|1[0-2])$/;
        if ($(this).val() === "" || reg.test($(this).val())) {
            $(this).css("background-color", "#fff");
            const color = $(this).parents("tr").find("td:eq(8) input").css("background-color");
            if (color != null && color === "rgb(255, 255, 0)")
                $(this).parents("tr").find("td:eq(8) input").css("background-color", "#fff");
        } else
            $(this).css("background-color", "#f00");
    });
    // 第九个单元格
    $("#tab2").on("input", "tr td:nth-child(9) input", function (event) {
        const reg = /^(0[0-9]|1[0-2])(0[1-9]|1[0-2])$/;
        if ($(this).val() === "" || reg.test($(this).val())) {
            $(this).css("background-color", "#fff");
            const color = $(this).parents("tr").find("td:eq(7) input").css("background-color");
            if (color != null && color === "rgb(255, 255, 0)")
                $(this).parents("tr").find("td:eq(7) input").css("background-color", "#fff");
        } else
            $(this).css("background-color", "#f00");
    });
    // 第十个单元格
    $("#tab2").on("input", "tr td:nth-child(10) input", function(event) {
    	if (isNaN($(this).val()) || $(this).val() > 100)
            $(this).css("background-color", "#f00");
        else
            $(this).css("background-color", "#fff");
    });
    // 第十一个单元格
    $("#tab2").on("input", "tr td:nth-child(11) input", function(event) {
    	if (isNaN($(this).val()))
            $(this).css("background-color", "#f00");
        else
            $(this).css("background-color", "#fff");
    });
    // 第十二个单元格
    $("#tab2").on("input", "tr td:nth-child(12) input", function(event) {
    	if ($(this).val() != "")
            $(this).css("background-color", "#fff");
    });
    $("#tab2").on("focus click", "tr td:nth-child(12) input", function(event) {
    	var value = $(this).parents("tr").find("td:eq(5) input").val();
        $(this).attr("list", value);
    });
    $("#tab2").on("keypress", "tr td:nth-child(4) input", function(event) {
    	if (event.which == 46 || (event.which >= 48 && event.which <= 57))
            return true;
        return false;
    });
    $("#tab2").on("keypress", "tr td:nth-child(8) input", function(event) {
    	if (event.which >= 48 && event.which <= 57)
            return true;
        return false;
    });
    $("#tab2").on("keypress", "tr td:nth-child(9) input", function(event) {
    	if (event.which >= 48 && event.which <= 57)
            return true;
        return false;
    });
    $("#tab2").on("keypress", "tr td:nth-child(10) input", function(event) {
    	if (event.which >= 48 && event.which <= 57)
            return true;
        return false;
    });
    $("#tab2").on("keypress", "tr td:nth-child(11) input", function(event) {
    	if (event.which >= 48 && event.which <= 57)
            return true;
        return false;
    });
    
    initItemList(0);
    function initItemList(index) {
        itemindex = -1;
        $("#tab2 tbody tr").each(function(i) {
            $(this).find("td:eq(2) input[type=text]").css("width", "75%");
            $(this).find("td:eq(2) input[type=text]").css("float", "left");
            $(this).find("td:eq(1) input[type=text]").attr("readonly", true);
            $(this).find("td:eq(2) input[type=text]").attr("readonly", true);
            $(this).find("td:eq(1) input[type=text]").attr("tabIndex", -1);
            $(this).find("td:eq(2) input[type=text]").attr("tabIndex", -1);
            $(this).find("td:eq(1) input[type=text]").css("cursor", "pointer");
            $(this).find("td:eq(2) input[type=text]").css("cursor", "pointer");
            $(this).find("td:eq(4) input[type=text]").attr("list", "conts");
            $(this).find("td:eq(5) input[type=text]").attr("list", "codes");
            $(this).find("td:eq(12) ").css("display", "none");
            /***************************************************************************/
            $(this).find("td").each(function(j) {
                $(this).attr("tabindex", i * 12 + j + 1);
            });
            var value = $(this).find("td:eq(3) input").val();
            if (value!= null && value != "") {
            	value = parseFloat(value).toFixed(2);
            	$(this).find("td:eq(3) input").val(value);
            }
        });
        if (index > $("#tab2 tbody tr").length - 1)
            index = $("#tab2 tbody tr").length - 1;
        $("#tab2 tbody tr").eq(index).click();
    }
    /**************************************************************************/
    $("#log").click(function() {
    	if ($("#tab3 tr").length == 0) {
    		var context = "";
    		var data = Ajax("/cctv/loginfo/findlist", {id: id1});
    		for (var i = 0; i < data.length; i++) {
    			context += "<tr>";
    			context += "  <td style='width:13%' valign='top'>[" + data[i].date + "]</td>";
    			context += "  <td style='width:75%' align=left>" + data[i].context + "</td>";
    			context += "  <td style='width:12%'>" + data[i].user.name + "</td>";
    			context += "</tr>";
			}
    		console.log(context);
    		$("#tab3").html(context);
    	}
    	$("#page").show();
    });
    $(document).keydown(function(event){
    	if (event.keyCode == 27 && $("#page:visible"))
    		$("#page").hide();
    });
    
    if (sessionStorage.control == "隐藏") {
 	   	$("#showItem").css("height", "756px");
 	   	$("#ishow").text("＋");
 	   	$("#showvnp").hide();
    }
    $("#ishow").click(function() {
    	if ($(this).text() == "－") {
        	sessionStorage.control = "隐藏";
            $("#showItem").css("height", "756px");
            $("#ishow").text("＋");
            $("#showvnp").hide();
        } else {
        	sessionStorage.control = "展开";
            $("#showItem").css("height", "210px");
            $("#ishow").text("－");
            $("#showvnp").show();
        }
    });
    
    function checkItem() {
        var result = true;
        var showText = language == "zh" ? "請輸入完整數據！" : "Please check the input data!";
        for (var i = 0; i < $("#tab2 tbody tr").length; i++) {
        	var line = $("#tab2 tbody tr").eq(i);
        	var dist = line.find("td:eq(3) input").val();
            var cont = line.find("td:eq(4) input").val();
            var code = line.find("td:eq(5) input").val();
            var clockAt = line.find("td:eq(7) input").val();
            var clockTo = line.find("td:eq(8) input").val();
            var intrusion1 = line.find("td:eq(9) input").val();
            var intrusion2 = line.find("td:eq(10) input").val();
            var remarks = line.find("td:eq(11) input").val();
            
            if (dist == "" || isNaN(dist)) {
            	line.find("td:eq(3) input").css("background-color", "#f00");
            	result = false;
            }
            if (cont != "" && contlist.indexOf(cont) == -1) {
            	line.find("td:eq(4) input").css("background-color", "#f00");
            	result = false;
            }
            if (code == "" || codelist.indexOf(code) == -1) {
            	line.find("td:eq(5) input").css("background-color", "#f00");
            	result = false;
            }
            var reg = /^(0[1-9]|1[0-2])$/;
            if (clockAt != "" && !reg.test(clockAt)) {
            	line.find("td:eq(7) input").css("background-color", "#f00");
            	result = false;
            }
        	var reg = /^(0[0-9]|1[0-2])(0[1-9]|1[0-2])$/;
            if (clockTo != "" && !reg.test(clockTo)) {
            	line.find("td:eq(8) input").css("background-color", "#f00");
            	result = false;
            }
        	if (intrusion1 != "" && (isNaN(intrusion1) || intrusion1 < 0 || intrusion1 > 100)) {
        		line.find("td:eq(9) input").css("background-color", "#f00");
            	result = false;
        	}
        	if (intrusion2 != "" && isNaN(intrusion2)) {
        		line.find("td:eq(10) input").css("background-color", "#f00");
            	result = false;
        	}
        	/***********************************************************************/
        	if (codelist1.indexOf(code) != -1 && intrusion2 == "") {
                line.find("td:eq(10) input").css("background-color", "#FFFF00");
                result = false;
            } else if (codelist2.indexOf(code) != -1 && intrusion1 == "") {
                line.find("td:eq(9) input").css("background-color", "#FFFF00");
                result = false;
            } else if (codelist3.indexOf(code) != -1 && clockAt == "") {
                line.find("td:eq(7) input").css("background-color", "#FFFF00");
                result = false;
            } else if (codelist4.indexOf(code) != -1 && clockTo == "") {
                line.find("td:eq(8) input").css("background-color", "#FFFF00");
                result = false;
            } else if (codelist5.indexOf(code) != -1 && remarks == "") {
                line.find("td:eq(11) input").css("background-color", "#FFFF00");
                result = false;
            } else if (codelist6.indexOf(code) != -1 && (intrusion2 == "" || clockAt == "")) {
                line.find("td:eq(7) input").css("background-color", "#FFFF00");
                line.find("td:eq(10) input").css("background-color", "#FFFF00");
                result = false;
            } else if (codelist8.indexOf(code) != -1 && (intrusion1 == "" || clockTo == "")) {
                line.find("td:eq(8) input").css("background-color", "#FFFF00");
                line.find("td:eq(9) input").css("background-color", "#FFFF00");
                result = false;
            } else if (codelist9.indexOf(code) != -1 && (intrusion1 == "" || remarks == "")) {
                line.find("td:eq(9) input").css("background-color", "#FFFF00");
                line.find("td:eq(11) input").css("background-color", "#FFFF00");
                result = false;
            } else if (codelist10.indexOf(code) != -1 && (clockAt == "" || clockTo == "")) {
                line.find("td:eq(7) input").css("background-color", "#FFFF00");
                line.find("td:eq(8) input").css("background-color", "#FFFF00");
                result = false;
            } else if (codelist11.indexOf(code) != -1 && (clockTo == "" || remarks == "")) {
                line.find("td:eq(8) input").css("background-color", "#FFFF00");
                line.find("td:eq(11) input").css("background-color", "#FFFF00");
                result = false;
            } else if (codelist12.indexOf(code) != -1 && (intrusion1 == "" || intrusion2 == "" || clockAt == "")) {
                line.find("td:eq(7) input").css("background-color", "#FFFF00");
                line.find("td:eq(9) input").css("background-color", "#FFFF00");
                line.find("td:eq(10) input").css("background-color", "#FFFF00");
                result = false;
            } else if (codelist13.indexOf(code) != -1 && (intrusion2 == "" || clockAt == "" || remarks == "")) {
                line.find("td:eq(7) input").css("background-color", "#FFFF00");
                line.find("td:eq(10) input").css("background-color", "#FFFF00");
                line.find("td:eq(11) input").css("background-color", "#FFFF00");
                result = false;
            } else if (codelist14.indexOf(code) != -1 && (intrusion1 == "" || (clockAt == "" && clockTo == ""))) {
                line.find("td:eq(9) input").css("background-color", "#FFFF00");
                line.find("td:eq(7) input").css("background-color", "#FFFF00");
                line.find("td:eq(8) input").css("background-color", "#FFFF00");
                result = false;
            } else if (codelist15.indexOf(code) != -1 && (intrusion1 == "" || clockTo == "" || remarks == "")) {
                line.find("td:eq(8) input").css("background-color", "#FFFF00");
                line.find("td:eq(9) input").css("background-color", "#FFFF00");
                line.find("td:eq(11) input").css("background-color", "#FFFF00");
                result = false;
            } else if (codelist16.indexOf(code) != -1 && ((clockAt == "" && clockTo == "") || remarks == "")) {
                line.find("td:eq(7) input").css("background-color", "#FFFF00");
                line.find("td:eq(8) input").css("background-color", "#FFFF00");
                line.find("td:eq(11) input").css("background-color", "#FFFF00");
                result = false;
            } else if (codelist17.indexOf(code) != -1 && (intrusion1 == "" || intrusion2 == "" || (clockAt == "" && clockTo == ""))) {
                line.find("td:eq(9) input").css("background-color", "#FFFF00");
                line.find("td:eq(10) input").css("background-color", "#FFFF00");
                line.find("td:eq(7) input").css("background-color", "#FFFF00");
                line.find("td:eq(8) input").css("background-color", "#FFFF00");
                result = false;
            } else if (codelist18.indexOf(code) != -1 && (intrusion1 == "" || (clockAt == "" && clockTo == "") || remarks == "")) {
                line.find("td:eq(9) input").css("background-color", "#FFFF00");
                line.find("td:eq(7) input").css("background-color", "#FFFF00");
                line.find("td:eq(8) input").css("background-color", "#FFFF00");
                line.find("td:eq(11) input").css("background-color", "#FFFF00");
                result = false;
            } else if (codelist19.indexOf(code) != -1 && (intrusion1 == "" || intrusion2 == "" || (clockAt == "" && clockTo == "") || remarks == "")) {
                line.find("td:eq(7) input").css("background-color", "#FFFF00");
                line.find("td:eq(8) input").css("background-color", "#FFFF00");
                line.find("td:eq(9) input").css("background-color", "#FFFF00");
                line.find("td:eq(10) input").css("background-color", "#FFFF00");
                line.find("td:eq(11) input").css("background-color", "#FFFF00");
                result = false;
            }
        }
        if (result == false) {
            showTips(showText);
            return false;
        }
        for (var i = 0; i < $("#tab2 tbody tr").length; i++) {
        	var line = $("#tab2 tbody tr").eq(i);
            var code = line.find("td:eq(5) input").val();
            var intrusion = line.find("td:eq(9) input").val();
            if ((code == "EL" || code == "ELJ" || code == "ESL") && intrusion >= 5) {
                line.find("td:eq(9) input").css("background-color", "#FFFF00");
                showTips("Intrusion % < 5%");
                return false;
            } else if ((code == "EM" || code == "EMJ" || code == "ESM") && (intrusion < 5 || intrusion > 20)) {
                line.find("td:eq(9) input").css("background-color", "#FFFF00");
                showTips("Intrusion % = 5%～20%");
                return false;
            } else if ((code == "EH" || code == "EHJ" || code == "ESH") && intrusion < 20) {
                line.find("td:eq(9) input").css("background-color", "#FFFF00");
                showTips("Intrusion % > 20%");
                return false;
            }
        }
        for (var i = 0; i < $("#tab2 tbody tr").length; i++) {
            var line = $("#tab2 tbody tr").eq(i);
            var dist = line.find("td:eq(3) input").val();
            var cont = line.find("td:eq(4) input").val();
            var code = line.find("td:eq(5) input").val();
            if (cont != null && cont != "") 
            	continue;
            if (code != "DEC" && code != "DES")
            	continue;
            for (var j = i + 1; j < $("#tab2 tbody tr").length; j++) {
                var temp = $("#tab2 tbody tr").eq(j);
                var idist = temp.find("td:eq(3) input").val();
                var icode = temp.find("td:eq(5) input").val();
                var length = idist - dist;
                if (code != icode || length > 1)
                	continue;
            	line.find("td:eq(3) input").css("background-color", "#ff0");
            	line.find("td:eq(5) input").css("background-color", "#ff0");
            	temp.find("td:eq(3) input").css("background-color", "#ff0");
            	temp.find("td:eq(5) input").css("background-color", "#ff0");
            	showTips("間隔需要大於1m");
            	return false;
            }
        }
        return true;
    }
    /** ******************************************************************** */
    /** 設置最大值 */
    var list = new Array();
    $("#tab2 tbody tr").each(function(i) {
        list.push($(this).find("td:eq(3) input").val());
    });
    var value = Math.max.apply(null, list);
    var length = value == "-Infinity" ? 0 : value;
    $("input[name=testlength]").val(length);
    /** ***************************************************************************** */
    function drawPipe() {
        var canvas = $("#showpipeimg")[0];
        var context = canvas.getContext("2d");
        context.font = "12px Courier New";
        context.clearRect(0, 0, canvas.width, canvas.height);
        context.beginPath();
        context.fillStyle = "#A1A1A1";
        context.strokeStyle = "#000000";
        context.rect(30, 100, 30, 640);
        context.fillRect(31, 101, 28, 638);
        context.fillStyle = "#A0A0A0";
        context.strokeStyle = "#606060";
        
        var tl = 0.0;
        var distlist = new Array();
        var joinlist = new Array();
        $("#tab2 tbody tr").each(function() {
            if (Number($(this).find("td:eq(3) input").val()) > tl)
                tl = $(this).find("td:eq(3) input").val();
            if ($(this).find("td:eq(5) input").val() == "MH")
                distlist.push($(this).find("td:eq(3) input").val());
            if ($(this).find("td:eq(5) input").val() == "JN")
                joinlist.push($(this).find("td:eq(3) input").val());
        });
        // var tl=Math.max.apply(null,distlist);
        tl = (tl == 0.0 ? 1 : tl);
        var use = $("#value3 select:eq(0)").val();
        for (var i = 0; i < distlist.length; i++) {
            if (use == "F") {
                var distance = i > 0 ? 100 : 40;
                var location = distlist[i] / tl * 640 + distance;
                context.fillRect(15, location, 60, 60);
            } else {
                var distance = i > 0 ? 130 : 70;
                var location = distlist[i] / tl * 640 + distance;
                context.moveTo(75, location);
                context.arc(45, location, 30, 0, Math.PI * 2);
                context.fill();
            }
        }
        for (var i = 0; i < joinlist.length; i++) {
            var location = joinlist[i] / tl * 640 + 90;
            context.fillRect(10, location, 19, 10);
        }
        function Note(dist, code) {
            this.dist = dist;
            this.code = code;
        }
        var list = new Array();
        $("#tab2 tbody tr").each(function() {
            var dist = $(this).find("input:eq(3)").val();
            var code = $(this).find("input:eq(5)").val();
            var note = new Note(dist,code);
            list.push(note);
        });

        var i = 0;
        var itemlength = 100;
        context.fillStyle = "#000000";
        while (i < list.length) {
            var distance = Math.round(list[i].dist / tl * 640 + 100);
            location = distance - itemlength < 0 ? itemlength : distance;
            itemlength = location + 15;
            context.moveTo(30, distance);
            context.lineTo(60, distance);
            context.moveTo(60, distance);
            context.lineTo(110, location);
            context.lineTo(125, location);
            context.fillText(list[i].dist, 130, location + 4);
            context.fillText(list[i].code, 170, location + 4);
            i++;
        }
        context.stroke();
        context.closePath();
    }

    /** 返回頂部 */
    $("#toTop").click(function() {
        $("body,html").animate({
            scrollTop: 0
        }, 100);
    });
    /** 顯示提示信息 */
    function showTips(text) {
        $("#tips").show().delay(1800).hide(200);
        $("#tips").text(text);
    }
    /** ***************************************************************** */
    /** 執行AJAX操作 */
    function Ajax(url, data) {
        var result = null;
        $.ajax({
            url: url,
            data: data,
            type: "post",
            async: false,
            datatype: "json",
            success: function(data) {
                result = data;
            }
        });
        return result;
    }
    /** ***************************************************************** */
    /** 創建列表 */
    function mikeDataList(value) {
        var text = "<datalist id='conts'>";
        for (var i = 1; i < 10; i++) {
            text += "<option>S" + i + "</option>";
            text += "<option>C" + i + "</option>";
            text += "<option>F" + i + "</option>";
        }
        text += "</datalist>";
        $("body").append(text);
        /** **************************** */
        var list = "<datalist id='B'>";
        list += "<option>DEFECT WANDERS</option>";
        list += "<option>LIVE</option>";
        list += "<option>POORLY FITTED</option>";
        list += "<option>VOID</option>";
        list += "</datalist>";
        $("body").append(list);

        var list = "<datalist id='BJ'>";
        list += "<option>DEFECT WANDERS</option>";
        list += "<option>LIVE</option>";
        list += "<option>POORLY FITTED</option>";
        list += "<option>VOID</option>";
        list += "</datalist>";
        $("body").append(list);

        var list = "<datalist id='BR'>";
        list += "<option>UNCHARTED MAJOR BRANCH</option>";
        list += "<option>UNCHARTED CATCHPIT</option>";
        list += "<option>UNCHARTED CHAMBER</option>";
        list += "<option>UNCHARTED GULLY</option>";
        list += "<option>UNCHARTED LAMPHOLE</option>";
        list += "<option>UNCHARTED MANHOLE</option>";
        list += "<option>UNCHARTED OIL SEPARATOR</option>";
        list += "<option>UNCHARTED RODDING EYE</option>";
        list += "<option>UNCHARTED OUTFALL</option>";
        list += "<option>UNCHARTED SOAKAWAY</option>";
        list += "<option>UNCHARTED NODE</option>";
        list += "</datalist>";
        $("body").append(list);

        var list = "<datalist id='CC'>";
        list += "<option>DEFECT WANDERS</option>";
        list += "<option>LIVE</option>";
        list += "<option>POORLY FITTED</option>";
        list += "<option>VOID</option>";
        list += "</datalist>";
        $("body").append(list);

        var list = "<datalist id='CCJ'>";
        list += "<option>DEFECT WANDERS</option>";
        list += "<option>LIVE</option>";
        list += "<option>POORLY FITTED</option>";
        list += "<option>VOID</option>";
        list += "</datalist>";
        $("body").append(list);

        var list = "<datalist id='CM'>";
        list += "<option>DEFECT WANDERS</option>";
        list += "<option>LIVE</option>";
        list += "<option>POORLY FITTED</option>";
        list += "<option>VOID</option>";
        list += "</datalist>";
        $("body").append(list);

        var list = "<datalist id='CMJ'>";
        list += "<option>DEFECT WANDERS</option>";
        list += "<option>LIVE</option>";
        list += "<option>POORLY FITTED</option>";
        list += "<option>VOID</option>";
        list += "</datalist>";
        $("body").append(list);

        var list = "<datalist id='CN'>";
        list += "<option>FROM BEHIND CONNECTION</option>";
        list += "<option>FROM FRACTURE</option>";
        list += "<option>LIVE</option>";
        list += "</datalist>";
        $("body").append(list);

        var list = "<datalist id='CP'>";
        list += "<option>UNCHARTED MAJOR BRANCH</option>";
        list += "<option>UNCHARTED CATCHPIT</option>";
        list += "<option>UNCHARTED CHAMBER</option>";
        list += "<option>UNCHARTED GULLY</option>";
        list += "<option>UNCHARTED LAMPHOLE</option>";
        list += "<option>UNCHARTED MANHOLE</option>";
        list += "<option>UNCHARTED OIL SEPARATOR</option>";
        list += "<option>UNCHARTED RODDING EYE</option>";
        list += "<option>UNCHARTED OUTFALL</option>";
        list += "<option>UNCHARTED SOAKAWAY</option>";
        list += "<option>UNCHARTED NODE</option>";
        list += "</datalist>";
        $("body").append(list);

        var list = "<datalist id='CS'>";
        list += "<option>DEFECT WANDERS</option>";
        list += "<option>LIVE</option>";
        list += "<option>POORLY FITTED</option>";
        list += "<option>VOID</option>";
        list += "</datalist>";
        $("body").append(list);

        var list = "<datalist id='CSJ'>";
        list += "<option>DEFECT WANDERS</option>";
        list += "<option>LIVE</option>";
        list += "<option>POORLY FITTED</option>";
        list += "<option>VOID</option>";
        list += "</datalist>";
        $("body").append(list);

        var list = "<datalist id='CXB'>";
        list += "<option>DEFECT WANDERS</option>";
        list += "<option>LIVE</option>";
        list += "<option>POORLY FITTED</option>";
        list += "<option>VOID</option>";
        list += "</datalist>";
        $("body").append(list);

        var list = "<datalist id='CXD'>";
        list += "<option>DEFECT WANDERS</option>";
        list += "<option>LIVE</option>";
        list += "<option>POORLY FITTED</option>";
        list += "<option>VOID</option>";
        list += "</datalist>";
        $("body").append(list);

        var list = "<datalist id='CXI'>";
        list += "<option>UNCHARTED MAJOR BRANCH</option>";
        list += "<option>UNCHARTED CATCHPIT</option>";
        list += "<option>UNCHARTED CHAMBER</option>";
        list += "<option>UNCHARTED GULLY</option>";
        list += "<option>UNCHARTED LAMPHOLE</option>";
        list += "<option>UNCHARTED MANHOLE</option>";
        list += "<option>UNCHARTED OIL SEPARATOR</option>";
        list += "<option>UNCHARTED RODDING EYE</option>";
        list += "<option>UNCHARTED OUTFALL</option>";
        list += "<option>UNCHARTED SOAKAWAY</option>";
        list += "<option>UNCHARTED NODE</option>";
        list += "</datalist>";
        $("body").append(list);

        var list = "<datalist id='CXP'>";
        list += "<option>DEFECT WANDERS</option>";
        list += "<option>LIVE</option>";
        list += "<option>POORLY FITTED</option>";
        list += "<option>VOID</option>";
        list += "</datalist>";
        $("body").append(list);

        var list = "<datalist id='CXZ'>";
        list += "<option>DEFECT WANDERS</option>";
        list += "<option>LIVE</option>";
        list += "<option>POORLY FITTED</option>";
        list += "<option>VOID</option>";
        list += "</datalist>";
        $("body").append(list);

        var list = "<datalist id='CZ'>";
        list += "<option>UNCHARTED MAJOR BRANCH</option>";
        list += "<option>UNCHARTED CATCHPIT</option>";
        list += "<option>UNCHARTED CHAMBER</option>";
        list += "<option>UNCHARTED GULLY</option>";
        list += "<option>UNCHARTED LAMPHOLE</option>";
        list += "<option>UNCHARTED MANHOLE</option>";
        list += "<option>UNCHARTED OIL SEPARATOR</option>";
        list += "<option>UNCHARTED RODDING EYE</option>";
        list += "<option>UNCHARTED OUTFALL</option>";
        list += "<option>UNCHARTED SOAKAWAY</option>";
        list += "<option>UNCHARTED NODE</option>";
        list += "</datalist>";
        $("body").append(list);

        var list = "<datalist id='DAF'>";
        list += "<option>FROM CONNECTION</option>";
        list += "<option>FROM FRACTURE</option>";
        list += "<option>RAGS</option>";
        list += "<option>STONES ETC…</option>";
        list += "<option>TIMES VARY</option>";
        list += "</datalist>";
        $("body").append(list);

        var list = "<datalist id='DAG'>";
        list += "<option>FROM CONNECTION</option>";
        list += "<option>FROM FRACTURE</option>";
        list += "<option>RAGS</option>";
        list += "<option>STONES ETC…</option>";
        list += "<option>TIMES VARY</option>";
        list += "</datalist>";
        $("body").append(list);

        var list = "<datalist id='DAZ'>";
        list += "<option>FROM CONNECTION</option>";
        list += "<option>FROM FRACTURE</option>";
        list += "<option>RAGS</option>";
        list += "<option>STONES ETC…</option>";
        list += "<option>TIMES VARY</option>";
        list += "</datalist>";
        $("body").append(list);

        var list = "<datalist id='DB'>";
        list += "<option>DEFECT WANDERS</option>";
        list += "<option>LIVE</option>";
        list += "<option>POORLY FITTED</option>";
        list += "<option>VOID</option>";
        list += "</datalist>";
        $("body").append(list);

        var list = "<datalist id='DEC'>";
        list += "<option>FROM CONNECTION</option>";
        list += "<option>FROM FRACTURE</option>";
        list += "<option>RAGS</option>";
        list += "<option>STONES ETC…</option>";
        list += "<option>TIMES VARY</option>";
        list += "</datalist>";
        $("body").append(list);

        var list = "<datalist id='DER'>";
        list += "<option>FROM CONNECTION</option>";
        list += "<option>FROM FRACTURE</option>";
        list += "<option>RAGS</option>";
        list += "<option>STONES ETC…</option>";
        list += "<option>TIMES VARY</option>";
        list += "</datalist>";
        $("body").append(list);

        var list = "<datalist id='DES'>";
        list += "<option>FROM CONNECTION</option>";
        list += "<option>FROM FRACTURE</option>";
        list += "<option>RAGS</option>";
        list += "<option>STONES ETC…</option>";
        list += "<option>TIMES VARY</option>";
        list += "</datalist>";
        $("body").append(list);

        var list = "<datalist id='DEZ'>";
        list += "<option>FROM CONNECTION</option>";
        list += "<option>FROM FRACTURE</option>";
        list += "<option>RAGS</option>";
        list += "<option>STONES ETC…</option>";
        list += "<option>TIMES VARY</option>";
        list += "</datalist>";
        $("body").append(list);

        var list = "<datalist id='DH'>";
        list += "<option>DEFECT WANDERS</option>";
        list += "<option>LIVE</option>";
        list += "<option>POORLY FITTED</option>";
        list += "<option>VOID</option>";
        list += "</datalist>";
        $("body").append(list);

        var list = "<datalist id='DI'>";
        list += "<option>RAT</option>";
        list += "<option>SHARP</option>";
        list += "<option>SLIGHT</option>";
        list += "<option>STEEP</option>";
        list += "<option>SYPHON</option>";
        list += "</datalist>";
        $("body").append(list);

        var list = "<datalist id='DV'>";
        list += "<option>DEFECT WANDERS</option>";
        list += "<option>LIVE</option>";
        list += "<option>POORLY FITTED</option>";
        list += "<option>VOID</option>";
        list += "</datalist>";
        $("body").append(list);

        var list = "<datalist id='EH'>";
        list += "<option>AT JOINT</option>";
        list += "<option>CONCRETE</option>";
        list += "<option>GROUT</option>";
        list += "</datalist>";
        $("body").append(list);

        var list = "<datalist id='EHJ'>";
        list += "<option>AT JOINT</option>";
        list += "<option>CONCRETE</option>";
        list += "<option>GROUT</option>";
        list += "</datalist>";
        $("body").append(list);

        var list = "<datalist id='EL'>";
        list += "<option>AT JOINT</option>";
        list += "<option>CONCRETE</option>";
        list += "<option>GROUT</option>";
        list += "</datalist>";
        $("body").append(list);

        var list = "<datalist id='ELJ'>";
        list += "<option>AT JOINT</option>";
        list += "<option>CONCRETE</option>";
        list += "<option>GROUT</option>";
        list += "</datalist>";
        $("body").append(list);

        var list = "<datalist id='EM'>";
        list += "<option>AT JOINT</option>";
        list += "<option>CONCRETE</option>";
        list += "<option>GROUT</option>";
        list += "</datalist>";
        $("body").append(list);

        var list = "<datalist id='EMJ'>";
        list += "<option>AT JOINT</option>";
        list += "<option>CONCRETE</option>";
        list += "<option>GROUT</option>";
        list += "</datalist>";
        $("body").append(list);

        var list = "<datalist id='ESH'>";
        list += "<option>AT JOINT</option>";
        list += "<option>CONCRETE</option>";
        list += "<option>GROUT</option>";
        list += "</datalist>";
        $("body").append(list);

        var list = "<datalist id='ESL'>";
        list += "<option>AT JOINT</option>";
        list += "<option>CONCRETE</option>";
        list += "<option>GROUT</option>";
        list += "</datalist>";
        $("body").append(list);

        var list = "<datalist id='ESM'>";
        list += "<option>AT JOINT</option>";
        list += "<option>CONCRETE</option>";
        list += "<option>GROUT</option>";
        list += "</datalist>";
        $("body").append(list);

        var list = "<datalist id='EX'>";
        list += "<option>DEFECT WANDERS</option>";
        list += "<option>LIVE</option>";
        list += "<option>POORLY FITTED</option>";
        list += "<option>VOID</option>";
        list += "</datalist>";
        $("body").append(list);

        var list = "<datalist id='EXJ'>";
        list += "<option>DEFECT WANDERS</option>";
        list += "<option>LIVE</option>";
        list += "<option>POORLY FITTED</option>";
        list += "<option>VOID</option>";
        list += "</datalist>";
        $("body").append(list);

        var list = "<datalist id='FC'>";
        list += "<option>DEFECT WANDERS</option>";
        list += "<option>LIVE</option>";
        list += "<option>POORLY FITTED</option>";
        list += "<option>VOID</option>";
        list += "</datalist>";
        $("body").append(list);

        var list = "<datalist id='FCJ'>";
        list += "<option>DEFECT WANDERS</option>";
        list += "<option>LIVE</option>";
        list += "<option>POORLY FITTED</option>";
        list += "<option>VOID</option>";

        var list = "<datalist id='FIC'>";
        list += "<option>FROM CONNECTION</option>";
        list += "<option>FROM FRACTURE</option>";
        list += "<option>RAGS</option>";
        list += "<option>STONES ETC…</option>";
        list += "<option>TIMES VARY</option>";
        list += "</datalist>";
        $("body").append(list);

        var list = "<datalist id='FIT'>";
        list += "<option>FROM CONNECTION</option>";
        list += "<option>FROM FRACTURE</option>";
        list += "<option>RAGS</option>";
        list += "<option>STONES ETC…</option>";
        list += "<option>TIMES VARY</option>";
        list += "</datalist>";
        $("body").append(list);

        var list = "<datalist id='FL'>";
        list += "<option>DEFECT WANDERS</option>";
        list += "<option>LIVE</option>";
        list += "<option>POORLY FITTED</option>";
        list += "<option>VOID</option>";
        list += "</datalist>";
        $("body").append(list);

        var list = "<datalist id='FLJ'>";
        list += "<option>DEFECT WANDERS</option>";
        list += "<option>LIVE</option>";
        list += "<option>POORLY FITTED</option>";
        list += "<option>VOID</option>";
        list += "</datalist>";
        $("body").append(list);

        var list = "<datalist id='FM'>";
        list += "<option>DEFECT WANDERS</option>";
        list += "<option>LIVE</option>";
        list += "<option>POORLY FITTED</option>";
        list += "<option>VOID</option>";
        list += "</datalist>";
        $("body").append(list);

        var list = "<datalist id='FMJ'>";
        list += "<option>DEFECT WANDERS</option>";
        list += "<option>LIVE</option>";
        list += "<option>POORLY FITTED</option>";
        list += "<option>VOID</option>";
        list += "</datalist>";
        $("body").append(list);

        var list = "<datalist id='FS'>";
        list += "<option>DEFECT WANDERS</option>";
        list += "<option>LIVE</option>";
        list += "<option>POORLY FITTED</option>";
        list += "<option>VOID</option>";

        var list = "<datalist id='FSJ'>";
        list += "<option>DEFECT WANDERS</option>";
        list += "<option>LIVE</option>";
        list += "<option>POORLY FITTED</option>";
        list += "<option>VOID</option>";
        list += "</datalist>";
        $("body").append(list);

        var list = "<datalist id='GY'>";
        list += "<option>UNCHARTED MAJOR BRANCH</option>";
        list += "<option>UNCHARTED CATCHPIT</option>";
        list += "<option>UNCHARTED CHAMBER</option>";
        list += "<option>UNCHARTED GULLY</option>";
        list += "<option>UNCHARTED LAMPHOLE</option>";
        list += "<option>UNCHARTED MANHOLE</option>";
        list += "<option>UNCHARTED OIL SEPARATOR</option>";
        list += "<option>UNCHARTED RODDING EYE</option>";
        list += "<option>UNCHARTED OUTFALL</option>";
        list += "<option>UNCHARTED SOAKAWAY</option>";
        list += "<option>UNCHARTED NODE</option>";
        list += "</datalist>";
        $("body").append(list);

        var list = "<datalist id='HA'>";
        list += "<option>DEFECT WANDERS</option>";
        list += "<option>LIVE</option>";
        list += "<option>POORLY FITTED</option>";
        list += "<option>VOID</option>";
        list += "</datalist>";
        $("body").append(list);

        var list = "<datalist id='HAJ'>";
        list += "<option>DEFECT WANDERS</option>";
        list += "<option>LIVE</option>";
        list += "<option>POORLY FITTED</option>";
        list += "<option>VOID</option>";
        list += "</datalist>";
        $("body").append(list);

        var list = "<datalist id='HB'>";
        list += "<option>DEFECT WANDERS</option>";
        list += "<option>LIVE</option>";
        list += "<option>POORLY FITTED</option>";
        list += "<option>VOID</option>";
        list += "</datalist>";
        $("body").append(list);

        var list = "<datalist id='HBJ'>";
        list += "<option>DEFECT WANDERS</option>";
        list += "<option>LIVE</option>";
        list += "<option>POORLY FITTED</option>";
        list += "<option>VOID</option>";
        list += "</datalist>";
        $("body").append(list);

        var list = "<datalist id='IC'>";
        list += "<option>UNCHARTED MAJOR BRANCH</option>";
        list += "<option>UNCHARTED CATCHPIT</option>";
        list += "<option>UNCHARTED CHAMBER</option>";
        list += "<option>UNCHARTED GULLY</option>";
        list += "<option>UNCHARTED LAMPHOLE</option>";
        list += "<option>UNCHARTED MANHOLE</option>";
        list += "<option>UNCHARTED OIL SEPARATOR</option>";
        list += "<option>UNCHARTED RODDING EYE</option>";
        list += "<option>UNCHARTED OUTFALL</option>";
        list += "<option>UNCHARTED SOAKAWAY</option>";
        list += "<option>UNCHARTED NODE</option>";
        list += "</datalist>";
        $("body").append(list);

        var list = "<datalist id='ID'>";
        list += "<option>FROM BEHIND CONNECTION</option>";
        list += "<option>FROM FRACTURE</option>";
        list += "<option>LIVE</option>";
        list += "</datalist>";
        $("body").append(list);

        var list = "<datalist id='IDJ'>";
        list += "<option>FROM BEHIND CONNECTION</option>";
        list += "<option>FROM FRACTURE</option>";
        list += "<option>LIVE</option>";
        list += "</datalist>";
        $("body").append(list);

        var list = "<datalist id='IG'>";
        list += "<option>FROM BEHIND CONNECTION</option>";
        list += "<option>FROM FRACTURE</option>";
        list += "<option>LIVE</option>";
        list += "</datalist>";
        $("body").append(list);

        var list = "<datalist id='IGJ'>";
        list += "<option>FROM BEHIND CONNECTION</option>";
        list += "<option>FROM FRACTURE</option>";
        list += "<option>LIVE</option>";
        list += "</datalist>";
        $("body").append(list);

        var list = "<datalist id='INF'>";
        list += "<option>FROM CONNECTION</option>";
        list += "<option>FROM FRACTURE</option>";
        list += "<option>RAGS</option>";
        list += "<option>STONES ETC…</option>";
        list += "<option>TIMES VARY</option>";
        list += "</datalist>";
        $("body").append(list);

        var list = "<datalist id='INFJ'>";
        list += "<option>FROM CONNECTION</option>";
        list += "<option>FROM FRACTURE</option>";
        list += "<option>RAGS</option>";
        list += "<option>STONES ETC…</option>";
        list += "<option>TIMES VARY</option>";

        var list = "<datalist id='ING'>";
        list += "<option>FROM CONNECTION</option>";
        list += "<option>FROM FRACTURE</option>";
        list += "<option>RAGS</option>";
        list += "<option>STONES ETC…</option>";
        list += "<option>TIMES VARY</option>";
        list += "</datalist>";
        $("body").append(list);

        var list = "<datalist id='INGJ'>";
        list += "<option>FROM CONNECTION</option>";
        list += "<option>FROM FRACTURE</option>";
        list += "<option>RAGS</option>";
        list += "<option>STONES ETC…</option>";
        list += "<option>TIMES VARY</option>";
        list += "</datalist>";
        $("body").append(list);

        var list = "<datalist id='INS'>";
        list += "<option>FROM CONNECTION</option>";
        list += "<option>FROM FRACTURE</option>";
        list += "<option>RAGS</option>";
        list += "<option>STONES ETC…</option>";
        list += "<option>TIMES VARY</option>";

        var list = "<datalist id='INSJ'>";
        list += "<option>FROM CONNECTION</option>";
        list += "<option>FROM FRACTURE</option>";
        list += "<option>RAGS</option>";
        list += "<option>STONES ETC…</option>";
        list += "<option>TIMES VARY</option>";
        list += "</datalist>";
        $("body").append(list);

        var list = "<datalist id='INZ'>";
        list += "<option>FROM CONNECTION</option>";
        list += "<option>FROM FRACTURE</option>";
        list += "<option>RAGS</option>";
        list += "<option>STONES ETC…</option>";
        list += "<option>TIMES VARY</option>";
        list += "</datalist>";
        $("body").append(list);

        var list = "<datalist id='INZJ'>";
        list += "<option>FROM CONNECTION</option>";
        list += "<option>FROM FRACTURE</option>";
        list += "<option>RAGS</option>";
        list += "<option>STONES ETC…</option>";
        list += "<option>TIMES VARY</option>";
        list += "</datalist>";
        $("body").append(list);

        var list = "<datalist id='IR'>";
        list += "<option>FROM BEHIND CONNECTION</option>";
        list += "<option>FROM FRACTURE</option>";
        list += "<option>LIVE</option>";
        list += "</datalist>";
        $("body").append(list);

        var list = "<datalist id='IS'>";
        list += "<option>FROM BEHIND CONNECTION</option>";
        list += "<option>FROM FRACTURE</option>";
        list += "<option>LIVE</option>";
        list += "</datalist>";
        $("body").append(list);

        var list = "<datalist id='IRJ'>";
        list += "<option>FROM BEHIND CONNECTION</option>";
        list += "<option>FROM FRACTURE</option>";
        list += "<option>LIVE</option>";
        list += "</datalist>";
        $("body").append(list);

        var list = "<datalist id='ISJ'>";
        list += "<option>FROM BEHIND CONNECTION</option>";
        list += "<option>FROM FRACTURE</option>";
        list += "<option>LIVE</option>";
        list += "</datalist>";
        $("body").append(list);

        var list = "<datalist id='JDL'>";
        list += "<option>DEFECT WANDERS</option>";
        list += "<option>LIVE</option>";
        list += "<option>POORLY FITTED</option>";
        list += "<option>VOID</option>";
        list += "</datalist>";
        $("body").append(list);

        var list = "<datalist id='JDM'>";
        list += "<option>DEFECT WANDERS</option>";
        list += "<option>LIVE</option>";
        list += "<option>POORLY FITTED</option>";
        list += "<option>VOID</option>";
        list += "</datalist>";
        $("body").append(list);

        var list = "<datalist id='JN'>";
        list += "<option>FROM BEHIND CONNECTION</option>";
        list += "<option>FROM FRACTURE</option>";
        list += "<option>LIVE</option>";
        list += "</datalist>";
        $("body").append(list);

        var list = "<datalist id='JXB'>";
        list += "<option>FROM BEHIND CONNECTION</option>";
        list += "<option>FROM FRACTURE</option>";
        list += "<option>LIVE</option>";
        list += "</datalist>";
        $("body").append(list);

        var list = "<datalist id='JXD'>";
        list += "<option>FROM BEHIND CONNECTION</option>";
        list += "<option>FROM FRACTURE</option>";
        list += "<option>LIVE</option>";
        list += "</datalist>";
        $("body").append(list);

        var list = "<datalist id='JXP'>";
        list += "<option>FROM BEHIND CONNECTION</option>";
        list += "<option>FROM FRACTURE</option>";
        list += "<option>LIVE</option>";
        list += "</datalist>";
        $("body").append(list);

        var list = "<datalist id='JXZ'>";
        list += "<option>FROM BEHIND CONNECTION</option>";
        list += "<option>FROM FRACTURE</option>";
        list += "<option>LIVE</option>";
        list += "</datalist>";
        $("body").append(list);

        var list = "<datalist id='LH'>";
        list += "<option>UNCHARTED MAJOR BRANCH</option>";
        list += "<option>UNCHARTED CATCHPIT</option>";
        list += "<option>UNCHARTED CHAMBER</option>";
        list += "<option>UNCHARTED GULLY</option>";
        list += "<option>UNCHARTED LAMPHOLE</option>";
        list += "<option>UNCHARTED MANHOLE</option>";
        list += "<option>UNCHARTED OIL SEPARATOR</option>";
        list += "<option>UNCHARTED RODDING EYE</option>";
        list += "<option>UNCHARTED OUTFALL</option>";
        list += "<option>UNCHARTED SOAKAWAY</option>";
        list += "<option>UNCHARTED NODE</option>";
        list += "</datalist>";
        $("body").append(list);

        var list = "<datalist id='MB'>";
        list += "<option>AT JOINT</option>";
        list += "<option>CONCRETE</option>";
        list += "<option>GROUT</option>";
        list += "</datalist>";
        $("body").append(list);

        var list = "<datalist id='MC'>";
        list += "<option>Asbestos Cement</option>";
        list += "<option>Bitumen Lining</option>";
        list += "<option>Brick</option>";
        list += "<option>Cast Iron</option>";
        list += "<option>Cement Mortar Lining</option>";
        list += "<option>Concrete</option>";
        list += "<option>Concrete Segments</option>";
        list += "<option>Ductile Iron</option>";
        list += "<option>Epoxy</option>";
        list += "<option>Fibre Cement</option>";
        list += "<option>Fibre Reinforced Plastics</option>";
        list += "<option>Galvanised Iron</option>";
        list += "<option>Masonry - in Regular Courses</option>";
        list += "<option>Masonry - in Randomly Coursed</option>";
        list += "<option>Polyvinyl Chloride</option>";
        list += "<option>Polyethylene</option>";
        list += "<option>Pitch Fibre</option>";
        list += "<option>Polypropylene</option>";
        list += "<option>Polyester</option>";
        list += "<option>Reinforced Concrete</option>";
        list += "<option>Sprayed Concrete</option>";
        list += "<option>Steel</option>";
        list += "<option>Vitrified Clay</option>";
        list += "<option>Unidentified Material</option>";
        list += "<option>Unidentified Type of Iron or Steel</option>";
        list += "<option>Unidentified Type of Plastics</option>";
        list += "<option>Other</option>";
        list += "</datalist>";
        $("body").append(list);

        var list = "<datalist id='ML'>";
        list += "<option>AT JOINT</option>";
        list += "<option>CONCRETE</option>";
        list += "<option>GROUT</option>";
        list += "</datalist>";
        $("body").append(list);

        var list = "<datalist id='MM'>";
        list += "<option>AT JOINT</option>";
        list += "<option>CONCRETE</option>";
        list += "<option>GROUT</option>";
        list += "</datalist>";
        $("body").append(list);

        var list = "<datalist id='MS'>";
        list += "<option>AT JOINT</option>";
        list += "<option>CONCRETE</option>";
        list += "<option>GROUT</option>";
        list += "</datalist>";
        $("body").append(list);

        var list = "<datalist id='OBB'>";
        list += "<option>FROM CONNECTION</option>";
        list += "<option>FROM FRACTURE</option>";
        list += "<option>RAGS</option>";
        list += "<option>STONES ETC…</option>";
        list += "<option>TIMES VARY</option>";
        list += "</datalist>";
        $("body").append(list);

        var list = "<datalist id='OBC'>";
        list += "<option>FROM CONNECTION</option>";
        list += "<option>FROM FRACTURE</option>";
        list += "<option>RAGS</option>";
        list += "<option>STONES ETC…</option>";
        list += "<option>TIMES VARY</option>";
        list += "</datalist>";
        $("body").append(list);

        var list = "<datalist id='OBI'>";
        list += "<option>FROM CONNECTION</option>";
        list += "<option>FROM FRACTURE</option>";
        list += "<option>RAGS</option>";
        list += "<option>STONES ETC…</option>";
        list += "<option>TIMES VARY</option>";
        list += "</datalist>";
        $("body").append(list);

        var list = "<datalist id='OBM'>";
        list += "<option>FROM CONNECTION</option>";
        list += "<option>FROM FRACTURE</option>";
        list += "<option>RAGS</option>";
        list += "<option>STONES ETC…</option>";
        list += "<option>TIMES VARY</option>";
        list += "</datalist>";
        $("body").append(list);

        var list = "<datalist id='OBP'>";
        list += "<option>FROM CONNECTION</option>";
        list += "<option>FROM FRACTURE</option>";
        list += "<option>RAGS</option>";
        list += "<option>STONES ETC…</option>";
        list += "<option>TIMES VARY</option>";
        list += "</datalist>";
        $("body").append(list);

        var list = "<datalist id='OBS'>";
        list += "<option>FROM CONNECTION</option>";
        list += "<option>FROM FRACTURE</option>";
        list += "<option>RAGS</option>";
        list += "<option>STONES ETC…</option>";
        list += "<option>TIMES VARY</option>";
        list += "</datalist>";
        $("body").append(list);

        var list = "<datalist id='OBZ'>";
        list += "<option>FROM CONNECTION</option>";
        list += "<option>FROM FRACTURE</option>";
        list += "<option>RAGS</option>";
        list += "<option>STONES ETC…</option>";
        list += "<option>TIMES VARY</option>";
        list += "</datalist>";
        $("body").append(list);

        var list = "<datalist id='OF'>";
        list += "<option>UNCHARTED MAJOR BRANCH</option>";
        list += "<option>UNCHARTED CATCHPIT</option>";
        list += "<option>UNCHARTED CHAMBER</option>";
        list += "<option>UNCHARTED GULLY</option>";
        list += "<option>UNCHARTED LAMPHOLE</option>";
        list += "<option>UNCHARTED MANHOLE</option>";
        list += "<option>UNCHARTED OIL SEPARATOR</option>";
        list += "<option>UNCHARTED RODDING EYE</option>";
        list += "<option>UNCHARTED OUTFALL</option>";
        list += "<option>UNCHARTED SOAKAWAY</option>";
        list += "<option>UNCHARTED NODE</option>";
        list += "</datalist>";
        $("body").append(list);

        var list = "<datalist id='OJL'>";
        list += "<option>FROM CONNECTION</option>";
        list += "<option>LIVE</option>";
        list += "<option>POORLY FITTED</option>";
        list += "<option>VOID</option>";
        list += "</datalist>";
        $("body").append(list);

        var list = "<datalist id='OJM'>";
        list += "<option>DEFECT WANDERS</option>";
        list += "<option>LIVE</option>";
        list += "<option>POORLY FITTED</option>";
        list += "<option>VOID</option>";
        list += "</datalist>";
        $("body").append(list);

        var list = "<datalist id='OS'>";
        list += "<option>UNCHARTED MAJOR BRANCH</option>";
        list += "<option>UNCHARTED CATCHPIT</option>";
        list += "<option>UNCHARTED CHAMBER</option>";
        list += "<option>UNCHARTED GULLY</option>";
        list += "<option>UNCHARTED LAMPHOLE</option>";
        list += "<option>UNCHARTED MANHOLE</option>";
        list += "<option>UNCHARTED OIL SEPARATOR</option>";
        list += "<option>UNCHARTED RODDING EYE</option>";
        list += "<option>UNCHARTED OUTFALL</option>";
        list += "<option>UNCHARTED SOAKAWAY</option>";
        list += "<option>UNCHARTED NODE</option>";
        list += "</datalist>";
        $("body").append(list);

        var list = "<datalist id='PP'>";
        list += "<option>FROM CONNECTION</option>";
        list += "<option>FROM FRACTURE</option>";
        list += "<option>RAGS</option>";
        list += "<option>STONES ETC…</option>";
        list += "<option>TIMES VARY</option>";
        list += "</datalist>";
        $("body").append(list);

        var list = "<datalist id='RE'>";
        list += "<option>UNCHARTED MAJOR BRANCH</option>";
        list += "<option>UNCHARTED CATCHPIT</option>";
        list += "<option>UNCHARTED CHAMBER</option>";
        list += "<option>UNCHARTED GULLY</option>";
        list += "<option>UNCHARTED LAMPHOLE</option>";
        list += "<option>UNCHARTED MANHOLE</option>";
        list += "<option>UNCHARTED OIL SEPARATOR</option>";
        list += "<option>UNCHARTED RODDING EYE</option>";
        list += "<option>UNCHARTED OUTFALL</option>";
        list += "<option>UNCHARTED SOAKAWAY</option>";
        list += "<option>UNCHARTED NODE</option>";
        list += "</datalist>";
        $("body").append(list);

        var list = "<datalist id='RF'>";
        list += "<option>FROM CONNECTION</option>";
        list += "<option>FROM FRACTURE</option>";
        list += "<option>RAGS</option>";
        list += "<option>STONES ETC…</option>";
        list += "<option>TIMES VARY</option>";
        list += "</datalist>";
        $("body").append(list);

        var list = "<datalist id='RFJ'>";
        list += "<option>FROM CONNECTION</option>";
        list += "<option>FROM FRACTURE</option>";
        list += "<option>RAGS</option>";
        list += "<option>STONES ETC…</option>";
        list += "<option>TIMES VARY</option>";
        list += "</datalist>";
        $("body").append(list);

        var list = "<datalist id='RM'>";
        list += "<option>FROM CONNECTION</option>";
        list += "<option>FROM FRACTURE</option>";
        list += "<option>RAGS</option>";
        list += "<option>STONES ETC…</option>";
        list += "<option>TIMES VARY</option>";
        list += "</datalist>";
        $("body").append(list);

        var list = "<datalist id='RPH'>";
        list += "<option>DEFECT WANDERS</option>";
        list += "<option>LIVE</option>";
        list += "<option>POORLY FITTED</option>";
        list += "<option>VOID</option>";
        list += "</datalist>";
        $("body").append(list);

        var list = "<datalist id='RPI'>";
        list += "<option>DEFECT WANDERS</option>";
        list += "<option>LIVE</option>";
        list += "<option>POORLY FITTED</option>";
        list += "<option>VOID</option>";
        list += "</datalist>";
        $("body").append(list);

        var list = "<datalist id='RPL'>";
        list += "<option>DEFECT WANDERS</option>";
        list += "<option>LIVE</option>";
        list += "<option>POORLY FITTED</option>";
        list += "<option>VOID</option>";
        list += "</datalist>";
        $("body").append(list);

        var list = "<datalist id='RPP'>";
        list += "<option>DEFECT WANDERS</option>";
        list += "<option>LIVE</option>";
        list += "<option>POORLY FITTED</option>";
        list += "<option>VOID</option>";
        list += "</datalist>";
        $("body").append(list);

        var list = "<datalist id='RPR'>";
        list += "<option>DEFECT WANDERS</option>";
        list += "<option>LIVE</option>";
        list += "<option>POORLY FITTED</option>";
        list += "<option>VOID</option>";
        list += "</datalist>";
        $("body").append(list);

        var list = "<datalist id='RPS'>";
        list += "<option>DEFECT WANDERS</option>";
        list += "<option>LIVE</option>";
        list += "<option>POORLY FITTED</option>";
        list += "<option>VOID</option>";
        list += "</datalist>";
        $("body").append(list);

        var list = "<datalist id='RPZ'>";
        list += "<option>DEFECT WANDERS</option>";
        list += "<option>LIVE</option>";
        list += "<option>POORLY FITTED</option>";
        list += "<option>VOID</option>";
        list += "</datalist>";
        $("body").append(list);

        var list = "<datalist id='RT'>";
        list += "<option>FROM CONNECTION</option>";
        list += "<option>FROM FRACTURE</option>";
        list += "<option>RAGS</option>";
        list += "<option>STONES ETC…</option>";
        list += "<option>TIMES VARY</option>";
        list += "</datalist>";
        $("body").append(list);

        var list = "<datalist id='RTJ'>";
        list += "<option>FROM CONNECTION</option>";
        list += "<option>FROM FRACTURE</option>";
        list += "<option>RAGS</option>";
        list += "<option>STONES ETC…</option>";
        list += "<option>TIMES VARY</option>";
        list += "</datalist>";
        $("body").append(list);

        var list = "<datalist id='RXM'>";
        list += "<option>DEFECT WANDERS</option>";
        list += "<option>LIVE</option>";
        list += "<option>POORLY FITTED</option>";
        list += "<option>VOID</option>";
        list += "</datalist>";
        $("body").append(list);

        var list = "<datalist id='RXZ'>";
        list += "<option>DEFECT WANDERS</option>";
        list += "<option>LIVE</option>";
        list += "<option>POORLY FITTED</option>";
        list += "<option>VOID</option>";
        list += "</datalist>";
        $("body").append(list);

        var list = "<datalist id='SAA'>";
        list += "<option>DUE TO BACKDROP</option>";
        list += "<option>DUE TO CAMERA UNDER WATER</option>";
        list += "<option>DUE TO COLLAPSE</option>";
        list += "<option>DUE TO DANGER TO EQUIPMENT</option>";
        list += "<option>DUE TO DEBRIS</option>";
        list += "<option>DUE TO DEBRIS GREASE</option>";
        list += "<option>DUE TO DEBRIS SILT</option>";
        list += "<option>DUE TO DEFORMED PIPE</option>";
        list += "<option>DUE TO DIAMETER CHANGE TO</option>";
        list += "<option>DUE TO DISPLACED JOINT LARGE</option>";
        list += "<option>DUE TO DISPLACED JOINT MEDIUM</option>";
        list += "<option>DUE TO ENCRUSTATION HEAVY</option>";
        list += "<option>DUE TO ENCRUSTATION MEDIUM</option>";
        list += "<option>DUE TO HOLE</option>";
        list += "<option>DUE TO INTRUDING CONNECTION</option>";
        list += "<option>DUE TO LINE DOWN</option>";
        list += "<option>DUE TO LINE LEFT</option>";
        list += "<option>DUE TO LINE RIGHT</option>";
        list += "<option>DUE TO LINE UP</option>";
        list += "<option>DUE TO LOSS OF TRACTION</option>";
        list += "<option>DUE TO MAIN LINE</option>";
        list += "<option>DUE TO MANHOLE IN SIGHT</option>";
        list += "<option>DUE TO MASS ROOTS</option>";
        list += "<option>DUE TO OBSTRUCTION</option>";
        list += "<option>DUE TO OUT OF SURVEY AREA</option>";
        list += "<option>DUE TO OVERLAP REACHED</option>";
        list += "<option>DUE TO SURFACE WEAR LARGE</option>";
        list += "<option>DUE TO SURFACE WEAR MEDIUM</option>";
        list += "<option>DUE TO SYPHON</option>";
        list += "<option>DUE TO UNCHARTED MANHOLE</option>";
        list += "</datalist>";
        $("body").append(list);

        var list = "<datalist id='SAC'>";
        list += "<option>DUE TO BACKDROP</option>";
        list += "<option>DUE TO CAMERA UNDER WATER</option>";
        list += "<option>DUE TO COLLAPSE</option>";
        list += "<option>DUE TO DANGER TO EQUIPMENT</option>";
        list += "<option>DUE TO DEBRIS</option>";
        list += "<option>DUE TO DEBRIS GREASE</option>";
        list += "<option>DUE TO DEBRIS SILT</option>";
        list += "<option>DUE TO DEFORMED PIPE</option>";
        list += "<option>DUE TO DIAMETER CHANGE TO</option>";
        list += "<option>DUE TO DISPLACED JOINT LARGE</option>";
        list += "<option>DUE TO DISPLACED JOINT MEDIUM</option>";
        list += "<option>DUE TO ENCRUSTATION HEAVY</option>";
        list += "<option>DUE TO ENCRUSTATION MEDIUM</option>";
        list += "<option>DUE TO HOLE</option>";
        list += "<option>DUE TO INTRUDING CONNECTION</option>";
        list += "<option>DUE TO LINE DOWN</option>";
        list += "<option>DUE TO LINE LEFT</option>";
        list += "<option>DUE TO LINE RIGHT</option>";
        list += "<option>DUE TO LINE UP</option>";
        list += "<option>DUE TO LOSS OF TRACTION</option>";
        list += "<option>DUE TO MAIN LINE</option>";
        list += "<option>DUE TO MANHOLE IN SIGHT</option>";
        list += "<option>DUE TO MASS ROOTS</option>";
        list += "<option>DUE TO OBSTRUCTION</option>";
        list += "<option>DUE TO OUT OF SURVEY AREA</option>";
        list += "<option>DUE TO OVERLAP REACHED</option>";
        list += "<option>DUE TO SURFACE WEAR LARGE</option>";
        list += "<option>DUE TO SURFACE WEAR MEDIUM</option>";
        list += "<option>DUE TO SYPHON</option>";
        list += "<option>DUE TO UNCHARTED MANHOLE</option>";
        list += "</datalist>";
        $("body").append(list);

        var list = "<datalist id='SAD'>";
        list += "<option>DUE TO BACKDROP</option>";
        list += "<option>DUE TO CAMERA UNDER WATER</option>";
        list += "<option>DUE TO COLLAPSE</option>";
        list += "<option>DUE TO DANGER TO EQUIPMENT</option>";
        list += "<option>DUE TO DEBRIS</option>";
        list += "<option>DUE TO DEBRIS GREASE</option>";
        list += "<option>DUE TO DEBRIS SILT</option>";
        list += "<option>DUE TO DEFORMED PIPE</option>";
        list += "<option>DUE TO DIAMETER CHANGE TO</option>";
        list += "<option>DUE TO DISPLACED JOINT LARGE</option>";
        list += "<option>DUE TO DISPLACED JOINT MEDIUM</option>";
        list += "<option>DUE TO ENCRUSTATION HEAVY</option>";
        list += "<option>DUE TO ENCRUSTATION MEDIUM</option>";
        list += "<option>DUE TO HOLE</option>";
        list += "<option>DUE TO INTRUDING CONNECTION</option>";
        list += "<option>DUE TO LINE DOWN</option>";
        list += "<option>DUE TO LINE LEFT</option>";
        list += "<option>DUE TO LINE RIGHT</option>";
        list += "<option>DUE TO LINE UP</option>";
        list += "<option>DUE TO LOSS OF TRACTION</option>";
        list += "<option>DUE TO MAIN LINE</option>";
        list += "<option>DUE TO MANHOLE IN SIGHT</option>";
        list += "<option>DUE TO MASS ROOTS</option>";
        list += "<option>DUE TO OBSTRUCTION</option>";
        list += "<option>DUE TO OUT OF SURVEY AREA</option>";
        list += "<option>DUE TO OVERLAP REACHED</option>";
        list += "<option>DUE TO SURFACE WEAR LARGE</option>";
        list += "<option>DUE TO SURFACE WEAR MEDIUM</option>";
        list += "<option>DUE TO SYPHON</option>";
        list += "<option>DUE TO UNCHARTED MANHOLE</option>";
        list += "</datalist>";
        $("body").append(list);

        var list = "<datalist id='SAS'>";
        list += "<option>DUE TO BACKDROP</option>";
        list += "<option>DUE TO CAMERA UNDER WATER</option>";
        list += "<option>DUE TO COLLAPSE</option>";
        list += "<option>DUE TO DANGER TO EQUIPMENT</option>";
        list += "<option>DUE TO DEBRIS</option>";
        list += "<option>DUE TO DEBRIS GREASE</option>";
        list += "<option>DUE TO DEBRIS SILT</option>";
        list += "<option>DUE TO DEFORMED PIPE</option>";
        list += "<option>DUE TO DIAMETER CHANGE TO</option>";
        list += "<option>DUE TO DISPLACED JOINT LARGE</option>";
        list += "<option>DUE TO DISPLACED JOINT MEDIUM</option>";
        list += "<option>DUE TO ENCRUSTATION HEAVY</option>";
        list += "<option>DUE TO ENCRUSTATION MEDIUM</option>";
        list += "<option>DUE TO HOLE</option>";
        list += "<option>DUE TO INTRUDING CONNECTION</option>";
        list += "<option>DUE TO LINE DOWN</option>";
        list += "<option>DUE TO LINE LEFT</option>";
        list += "<option>DUE TO LINE RIGHT</option>";
        list += "<option>DUE TO LINE UP</option>";
        list += "<option>DUE TO LOSS OF TRACTION</option>";
        list += "<option>DUE TO MAIN LINE</option>";
        list += "<option>DUE TO MANHOLE IN SIGHT</option>";
        list += "<option>DUE TO MASS ROOTS</option>";
        list += "<option>DUE TO OBSTRUCTION</option>";
        list += "<option>DUE TO OUT OF SURVEY AREA</option>";
        list += "<option>DUE TO OVERLAP REACHED</option>";
        list += "<option>DUE TO SURFACE WEAR LARGE</option>";
        list += "<option>DUE TO SURFACE WEAR MEDIUM</option>";
        list += "<option>DUE TO SYPHON</option>";
        list += "<option>DUE TO UNCHARTED MANHOLE</option>";
        list += "</datalist>";
        $("body").append(list);

        var list = "<datalist id='SAT'>";
        list += "<option>DUE TO BACKDROP</option>";
        list += "<option>DUE TO CAMERA UNDER WATER</option>";
        list += "<option>DUE TO COLLAPSE</option>";
        list += "<option>DUE TO DANGER TO EQUIPMENT</option>";
        list += "<option>DUE TO DEBRIS</option>";
        list += "<option>DUE TO DEBRIS GREASE</option>";
        list += "<option>DUE TO DEBRIS SILT</option>";
        list += "<option>DUE TO DEFORMED PIPE</option>";
        list += "<option>DUE TO DIAMETER CHANGE TO</option>";
        list += "<option>DUE TO DISPLACED JOINT LARGE</option>";
        list += "<option>DUE TO DISPLACED JOINT MEDIUM</option>";
        list += "<option>DUE TO ENCRUSTATION HEAVY</option>";
        list += "<option>DUE TO ENCRUSTATION MEDIUM</option>";
        list += "<option>DUE TO HOLE</option>";
        list += "<option>DUE TO INTRUDING CONNECTION</option>";
        list += "<option>DUE TO LINE DOWN</option>";
        list += "<option>DUE TO LINE LEFT</option>";
        list += "<option>DUE TO LINE RIGHT</option>";
        list += "<option>DUE TO LINE UP</option>";
        list += "<option>DUE TO LOSS OF TRACTION</option>";
        list += "<option>DUE TO MAIN LINE</option>";
        list += "<option>DUE TO MANHOLE IN SIGHT</option>";
        list += "<option>DUE TO MASS ROOTS</option>";
        list += "<option>DUE TO OBSTRUCTION</option>";
        list += "<option>DUE TO OUT OF SURVEY AREA</option>";
        list += "<option>DUE TO OVERLAP REACHED</option>";
        list += "<option>DUE TO SURFACE WEAR LARGE</option>";
        list += "<option>DUE TO SURFACE WEAR MEDIUM</option>";
        list += "<option>DUE TO SYPHON</option>";
        list += "<option>DUE TO UNCHARTED MANHOLE</option>";
        list += "</datalist>";
        $("body").append(list);

        var list = "<datalist id='SAZ'>";
        list += "<option>Due to debris / silt.</option>";
        list += "<option>Due to compacted / hard.</option>";
        list += "<option>Due to scale medium.</option>";
        list += "<option>Due to scale heavy.</option>";
        list += "<option>Due to camera under water.</option>";
        list += "<option>Due to high water level.</option>";
        list += "<option>Due to backdrop.</option>";
        list += "<option>Due to collapse.</option>";
        list += "<option>Due to danger to equipment.</option>";
        list += "<option>Due to debris.</option>";
        list += "<option>Due to debris grease.</option>";
        list += "<option>Due to deformed pipe.</option>";
        list += "<option>Due to diameter change to.</option>";
        list += "<option>Due to displaced joint large.</option>";
        list += "<option>Due to displaced joint medium.</option>";
        list += "<option>Due to encrustation heavy.</option>";
        list += "<option>Due to encrustation medium.</option>";
        list += "<option>Due to hole.</option>";
        list += "<option>Due to intruding connection.</option>";
        list += "<option>Due to line down.</option>";
        list += "<option>Due to line left.</option>";
        list += "<option>Due to line right.</option>";
        list += "<option>Due to line up.</option>";
        list += "<option>Due to loss of traction.</option>";
        list += "<option>Due to main line.</option>";
        list += "<option>Due to manhole in sight.</option>";
        list += "<option>Due to mass roots.</option>";
        list += "<option>Due to obstruction.</option>";
        list += "<option>Due to out of survey area.</option>";
        list += "<option>Due to overlap reached.</option>";
        list += "<option>Due to surface wear large.</option>";
        list += "<option>Due to surface wear medium.</option>";
        list += "<option>Due to syphon.</option>";
        list += "<option>Due to uncharted manhole.</option>";
        list += "</datalist>";
        $("body").append(list);

        var list = "<datalist id='SC'>";
        list += "<option>Arched with Flat Bottom</option>";
        list += "<option>Barrel</option>";
        list += "<option>Circular</option>";
        list += "<option>Egg shaped</option>";
        list += "<option>Horseshoe</option>";
        list += "<option>Oval</option>";
        list += "<option>Rectangular</option>";
        list += "<option>Square</option>";
        list += "<option>Trapezoidal</option>";
        list += "<option>U-shaped with Flat Top</option>";
        list += "<option>Kerb Block</option>";
        list += "<option>Others</option>";
        list += "</datalist>";
        $("body").append(list);

        var list = "<datalist id='SK'>";
        list += "<option>UNCHARTED MAJOR BRANCH</option>";
        list += "<option>UNCHARTED CATCHPIT</option>";
        list += "<option>UNCHARTED CHAMBER</option>";
        list += "<option>UNCHARTED GULLY</option>";
        list += "<option>UNCHARTED LAMPHOLE</option>";
        list += "<option>UNCHARTED MANHOLE</option>";
        list += "<option>UNCHARTED OIL SEPARATOR</option>";
        list += "<option>UNCHARTED RODDING EYE</option>";
        list += "<option>UNCHARTED OUTFALL</option>";
        list += "<option>UNCHARTED SOAKAWAY</option>";
        list += "<option>UNCHARTED NODE</option>";
        list += "</datalist>";
        $("body").append(list);

        var list = "<datalist id='SRB'>";
        list += "<option>DEFECT WANDERS</option>";
        list += "<option>LIVE</option>";
        list += "<option>POORLY FITTED</option>";
        list += "<option>VOID</option>";
        list += "</datalist>";
        $("body").append(list);

        var list = "<datalist id='SRI'>";
        list += "<option>DEFECT WANDERS</option>";
        list += "<option>LIVE</option>";
        list += "<option>POORLY FITTED</option>";
        list += "<option>VOID</option>";
        list += "</datalist>";
        $("body").append(list);

        var list = "<datalist id='SRZ'>";
        list += "<option>DEFECT WANDERS</option>";
        list += "<option>LIVE</option>";
        list += "<option>POORLY FITTED</option>";
        list += "<option>VOID</option>";
        list += "</datalist>";
        $("body").append(list);

        var list = "<datalist id='SSL'>";
        list += "<option>AT JOINT</option>";
        list += "<option>CONCRETE</option>";
        list += "<option>GROUT</option>";
        list += "</datalist>";
        $("body").append(list);

        var list = "<datalist id='SSM'>";
        list += "<option>AT JOINT</option>";
        list += "<option>CONCRETE</option>";
        list += "<option>GROUT</option>";
        list += "</datalist>";
        $("body").append(list);

        var list = "<datalist id='SSS'>";
        list += "<option>AT JOINT</option>";
        list += "<option>CONCRETE</option>";
        list += "<option>GROUT</option>";
        list += "</datalist>";
        $("body").append(list);

        var list = "<datalist id='SV'>";
        list += "<option>DEFECT WANDERS</option>";
        list += "<option>LIVE</option>";
        list += "<option>POORLY FITTED</option>";
        list += "<option>VOID</option>";
        list += "</datalist>";
        $("body").append(list);

        var list = "<datalist id='SVJ'>";
        list += "<option>DEFECT WANDERS</option>";
        list += "<option>LIVE</option>";
        list += "<option>POORLY FITTED</option>";
        list += "<option>VOID</option>";
        list += "</datalist>";
        $("body").append(list);

        var list = "<datalist id='SWL'>";
        list += "<option>AT JOINT</option>";
        list += "<option>CONCRETE</option>";
        list += "<option>GROUT</option>";
        list += "</datalist>";
        $("body").append(list);

        var list = "<datalist id='SWM'>";
        list += "<option>AT JOINT</option>";
        list += "<option>CONCRETE</option>";
        list += "<option>GROUT</option>";
        list += "</datalist>";
        $("body").append(list);

        var list = "<datalist id='SWS'>";
        list += "<option>AT JOINT</option>";
        list += "<option>CONCRETE</option>";
        list += "<option>GROUT</option>";
        list += "</datalist>";
        $("body").append(list);

        var list = "<datalist id='VV'>";
        list += "<option>DEFECT WANDERS</option>";
        list += "<option>LIVE</option>";
        list += "<option>POORLY FITTED</option>";
        list += "<option>VOID</option>";
        list += "</datalist>";
        $("body").append(list);

        var list = "<datalist id='VVJ'>";
        list += "<option>DEFECT WANDERS</option>";
        list += "<option>LIVE</option>";
        list += "<option>POORLY FITTED</option>";
        list += "<option>VOID</option>";
        list += "</datalist>";
        $("body").append(list);

        var list = "<datalist id='WXC'>";
        list += "<option>DEFECT WANDERS</option>";
        list += "<option>LIVE</option>";
        list += "<option>POORLY FITTED</option>";
        list += "<option>VOID</option>";
        list += "</datalist>";
        $("body").append(list);

        var list = "<datalist id='WXL'>";
        list += "<option>DEFECT WANDERS</option>";
        list += "<option>LIVE</option>";
        list += "<option>POORLY FITTED</option>";
        list += "<option>VOID</option>";
        list += "</datalist>";
        $("body").append(list);

        var list = "<datalist id='WXS'>";
        list += "<option>DEFECT WANDERS</option>";
        list += "<option>LIVE</option>";
        list += "<option>POORLY FITTED</option>";
        list += "<option>VOID</option>";
        list += "</datalist>";
        $("body").append(list);

        var list = "<datalist id='XB'>";
        list += "<option>DEFECT WANDERS</option>";
        list += "<option>LIVE</option>";
        list += "<option>POORLY FITTED</option>";
        list += "<option>VOID</option>";
        list += "</datalist>";
        $("body").append(list);

        var list = "<datalist id='XP'>";
        list += "<option>DEFECT WANDERS</option>";
        list += "<option>LIVE</option>";
        list += "<option>POORLY FITTED</option>";
        list += "<option>VOID</option>";
        list += "</datalist>";
        $("body").append(list);
        
        var list = "<datalist id='#4-1'>";
        list += "<option>Due to debris / silt.</option>";
        list += "<option>Due to compacted / hard.</option>";
        list += "<option>Due to scale medium.</option>";
        list += "<option>Due to scale heavy.</option>";
        list += "<option>Due to camera under water.</option>";
        list += "<option>Due to high water level.</option>";
        list += "<option>Due to backdrop.</option>";
        list += "<option>Due to collapse.</option>";
        list += "<option>Due to danger to equipment.</option>";
        list += "<option>Due to debris.</option>";
        list += "<option>Due to debris grease.</option>";
        list += "<option>Due to deformed pipe.</option>";
        list += "<option>Due to diameter change to.</option>";
        list += "<option>Due to displaced joint large.</option>";
        list += "<option>Due to displaced joint medium.</option>";
        list += "<option>Due to encrustation heavy.</option>";
        list += "<option>Due to encrustation medium.</option>";
        list += "<option>Due to hole.</option>";
        list += "<option>Due to intruding connection.</option>";
        list += "<option>Due to line down.</option>";
        list += "<option>Due to line left.</option>";
        list += "<option>Due to line right.</option>";
        list += "<option>Due to line up.</option>";
        list += "<option>Due to loss of traction.</option>";
        list += "<option>Due to main line.</option>";
        list += "<option>Due to manhole in sight.</option>";
        list += "<option>Due to mass roots.</option>";
        list += "<option>Due to obstruction.</option>";
        list += "<option>Due to out of survey area.</option>";
        list += "<option>Due to overlap reached.</option>";
        list += "<option>Due to surface wear large.</option>";
        list += "<option>Due to surface wear medium.</option>";
        list += "<option>Due to syphon.</option>";
        list += "<option>Due to uncharted manhole.</option>";
        list += "</datalist>";
        $("body").append(list);
        
        var list = "<datalist id='#4-2'>";
        list += "<option>Due to debris / silt.</option>";
        list += "<option>Due to compacted / hard.</option>";
        list += "<option>Due to scale medium.</option>";
        list += "<option>Due to scale heavy.</option>";
        list += "<option>Due to camera under water.</option>";
        list += "<option>Due to high water level.</option>";
        list += "<option>Due to backdrop.</option>";
        list += "<option>Due to collapse.</option>";
        list += "<option>Due to danger to equipment.</option>";
        list += "<option>Due to debris.</option>";
        list += "<option>Due to debris grease.</option>";
        list += "<option>Due to deformed pipe.</option>";
        list += "<option>Due to diameter change to.</option>";
        list += "<option>Due to displaced joint large.</option>";
        list += "<option>Due to displaced joint medium.</option>";
        list += "<option>Due to encrustation heavy.</option>";
        list += "<option>Due to encrustation medium.</option>";
        list += "<option>Due to hole.</option>";
        list += "<option>Due to intruding connection.</option>";
        list += "<option>Due to line down.</option>";
        list += "<option>Due to line left.</option>";
        list += "<option>Due to line right.</option>";
        list += "<option>Due to line up.</option>";
        list += "<option>Due to loss of traction.</option>";
        list += "<option>Due to main line.</option>";
        list += "<option>Due to manhole in sight.</option>";
        list += "<option>Due to mass roots.</option>";
        list += "<option>Due to obstruction.</option>";
        list += "<option>Due to out of survey area.</option>";
        list += "<option>Due to overlap reached.</option>";
        list += "<option>Due to surface wear large.</option>";
        list += "<option>Due to surface wear medium.</option>";
        list += "<option>Due to syphon.</option>";
        list += "<option>Due to uncharted manhole.</option>";
        list += "</datalist>";
        $("body").append(list);
        
        var list = "<datalist id='#4-3'>";
        list += "<option>Due to debris / silt.</option>";
        list += "<option>Due to compacted / hard.</option>";
        list += "<option>Due to scale medium.</option>";
        list += "<option>Due to scale heavy.</option>";
        list += "<option>Due to camera under water.</option>";
        list += "<option>Due to high water level.</option>";
        list += "<option>Due to backdrop.</option>";
        list += "<option>Due to collapse.</option>";
        list += "<option>Due to danger to equipment.</option>";
        list += "<option>Due to debris.</option>";
        list += "<option>Due to debris grease.</option>";
        list += "<option>Due to deformed pipe.</option>";
        list += "<option>Due to diameter change to.</option>";
        list += "<option>Due to displaced joint large.</option>";
        list += "<option>Due to displaced joint medium.</option>";
        list += "<option>Due to encrustation heavy.</option>";
        list += "<option>Due to encrustation medium.</option>";
        list += "<option>Due to hole.</option>";
        list += "<option>Due to intruding connection.</option>";
        list += "<option>Due to line down.</option>";
        list += "<option>Due to line left.</option>";
        list += "<option>Due to line right.</option>";
        list += "<option>Due to line up.</option>";
        list += "<option>Due to loss of traction.</option>";
        list += "<option>Due to main line.</option>";
        list += "<option>Due to manhole in sight.</option>";
        list += "<option>Due to mass roots.</option>";
        list += "<option>Due to obstruction.</option>";
        list += "<option>Due to out of survey area.</option>";
        list += "<option>Due to overlap reached.</option>";
        list += "<option>Due to surface wear large.</option>";
        list += "<option>Due to surface wear medium.</option>";
        list += "<option>Due to syphon.</option>";
        list += "<option>Due to uncharted manhole.</option>";
        list += "</datalist>";
        $("body").append(list);
        
        var list = "<datalist id='#4-4'>";
        list += "<option>Due to debris / silt.</option>";
        list += "<option>Due to compacted / hard.</option>";
        list += "<option>Due to scale medium.</option>";
        list += "<option>Due to scale heavy.</option>";
        list += "<option>Due to camera under water.</option>";
        list += "<option>Due to high water level.</option>";
        list += "<option>Due to backdrop.</option>";
        list += "<option>Due to collapse.</option>";
        list += "<option>Due to danger to equipment.</option>";
        list += "<option>Due to debris.</option>";
        list += "<option>Due to debris grease.</option>";
        list += "<option>Due to deformed pipe.</option>";
        list += "<option>Due to diameter change to.</option>";
        list += "<option>Due to displaced joint large.</option>";
        list += "<option>Due to displaced joint medium.</option>";
        list += "<option>Due to encrustation heavy.</option>";
        list += "<option>Due to encrustation medium.</option>";
        list += "<option>Due to hole.</option>";
        list += "<option>Due to intruding connection.</option>";
        list += "<option>Due to line down.</option>";
        list += "<option>Due to line left.</option>";
        list += "<option>Due to line right.</option>";
        list += "<option>Due to line up.</option>";
        list += "<option>Due to loss of traction.</option>";
        list += "<option>Due to main line.</option>";
        list += "<option>Due to manhole in sight.</option>";
        list += "<option>Due to mass roots.</option>";
        list += "<option>Due to obstruction.</option>";
        list += "<option>Due to out of survey area.</option>";
        list += "<option>Due to overlap reached.</option>";
        list += "<option>Due to surface wear large.</option>";
        list += "<option>Due to surface wear medium.</option>";
        list += "<option>Due to syphon.</option>";
        list += "<option>Due to uncharted manhole.</option>";
        list += "</datalist>";
        $("body").append(list);
        
        var list = "<datalist id='#4-5'>";
        list += "<option>Due to debris / silt.</option>";
        list += "<option>Due to compacted / hard.</option>";
        list += "<option>Due to scale medium.</option>";
        list += "<option>Due to scale heavy.</option>";
        list += "<option>Due to camera under water.</option>";
        list += "<option>Due to high water level.</option>";
        list += "<option>Due to backdrop.</option>";
        list += "<option>Due to collapse.</option>";
        list += "<option>Due to danger to equipment.</option>";
        list += "<option>Due to debris.</option>";
        list += "<option>Due to debris grease.</option>";
        list += "<option>Due to deformed pipe.</option>";
        list += "<option>Due to diameter change to.</option>";
        list += "<option>Due to displaced joint large.</option>";
        list += "<option>Due to displaced joint medium.</option>";
        list += "<option>Due to encrustation heavy.</option>";
        list += "<option>Due to encrustation medium.</option>";
        list += "<option>Due to hole.</option>";
        list += "<option>Due to intruding connection.</option>";
        list += "<option>Due to line down.</option>";
        list += "<option>Due to line left.</option>";
        list += "<option>Due to line right.</option>";
        list += "<option>Due to line up.</option>";
        list += "<option>Due to loss of traction.</option>";
        list += "<option>Due to main line.</option>";
        list += "<option>Due to manhole in sight.</option>";
        list += "<option>Due to mass roots.</option>";
        list += "<option>Due to obstruction.</option>";
        list += "<option>Due to out of survey area.</option>";
        list += "<option>Due to overlap reached.</option>";
        list += "<option>Due to surface wear large.</option>";
        list += "<option>Due to surface wear medium.</option>";
        list += "<option>Due to syphon.</option>";
        list += "<option>Due to uncharted manhole.</option>";
        list += "</datalist>";
        $("body").append(list);
    }
});
