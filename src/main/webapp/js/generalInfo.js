var rootPath = "../../";
var deptId = "";
var seriesDataA0 = 0;
var seriesDataA1 = [];
var seriesDataB1 = [];
var seriesDataB2 = [];
var seriesDataC1 = [];
var pageViews = {
    items: [{
        name: '基础数据',
        scrollTop: '0'
    }, {
        name: '专项数据',
        scrollTop: '280'
    }, {
        name : '网络巡查',
        scrollTop: '600'
    }],
    currentPage: 1,
    hasPrePage: function() {
        return this.currentPage > 1 && this.items.length > 1;
    },
    hasNextPage: function() {
        return this.items.length > this.currentPage;
    },
    goNext: function() {
        this.currentPage++;
        this.showPager();
        var views = $("#views div:first-child");
        $(views).scrollTop(this.items[this.currentPage - 1].scrollTop);
    },
    goPre: function() {
        this.currentPage--;
        this.showPager();
        var views = $("#views div:first-child");
        $(views).scrollTop(this.items[this.currentPage - 1].scrollTop);
    },
    showPager: function() {
        if (this.hasPrePage()) {
            $("#btnPre").show();
        } else {
            $("#btnPre").hide();
        }
        if (this.hasNextPage()) {
            $("#btnNext").show();
        } else {
            $("#btnNext").hide();
        }
    },
    init:function() {
        var me = this;
        $("#btnPre").on("click", function() {
            me.goPre();
        });
        $("#btnNext").on("click", function() {
            me.goNext();
        });
    }
};

$(function () {
    _loading.init();
    if (sessionStorage.getItem("staticIndex_deptLevel") == "3") {
        $("#goWgdt").show();
    }
    deptId = sessionStorage.getItem("staticIndex_deptId");
    getDatas();

    pageViews.init();
});

function getDatas() {
    _loading.show();
    $.ajax({
        url: rootPath + 'Outer/T_people_info/searchGeneralInfo.do',
        data: {deptId: deptId},
        dataType: 'json',
        type: 'GET',
        success: function (result) {
            var data = result.data;
            seriesDataA0 = parseInt(data.hjrk) + parseInt(data.ldrk) + parseInt(data.jzrk);
            seriesDataA1 = [
                {name: '户籍人口', y: parseInt(data.hjrk), color: '#58ACD6'},
                {name: '流动人口', y: parseInt(data.ldrk), color: '#D68B4E'},
                {name: '寄住人口', y: parseInt(data.jzrk), color: '#67D66A'}
            ];//基础数据
            seriesDataB1 = [parseInt(data.mdysb), parseInt(data.xfysb), parseInt(data.wsysb), parseInt(data.jzwzysb)];//网格巡查已发现问题
            seriesDataB2 = [parseInt(data.mdybj), parseInt(data.xfybj), parseInt(data.wsybj), parseInt(data.jzwzybj)];//网格巡查已办结问题
            seriesDataC1 = [parseInt(data.lnxx), parseInt(data.cjrxx), parseInt(data.dlsy), parseInt(data.dbxx),
                parseInt(data.syry), parseInt(data.syxx), parseInt(data.csye), parseInt(data.jsxx), parseInt(data.wcnr)];
            $("#dzName").text(data.dzName);
            $("#jcsjTime").text("截止 " + data.sta_time);
            $("#wgxcTime").text("截止 " + data.sta_time);
            $("#xxcjTime").text("截止 " + data.sta_time);

            loadHightChartsPie();
            loadHighChartsColumn();
            loadHighChartsBar();
            showZdzfrqDiv(data);

            _loading.hide();
        }
    });
}

function loadHightChartsPie() {
    $('#jcsjContent').highcharts({
        chart: {type: 'pie', backgroundColor: 'rgba(0,0,0,0)'},
        title: {floating: true, text: seriesDataA0},
        plotOptions: {
            pie: {
                shadow: false,
                center: ['50%', '50%'],
                dataLabels: {
                    //enable: false,
                    enable: true,
                    softConnector: false,
                    distance: 15,
                    y: -15,
                    formatter: function () {
                        return this.point.name + '：<br/>' + this.y + '人(' + Highcharts.numberFormat(this.percentage, 1) + '%)';
                    }
                }
            }
        },
        tooltip: {
            valueSuffix: '人',
            formatter: function () {
                return this.point.name + '：<b>' + this.y + '</b>人(' + Highcharts.numberFormat(this.percentage, 1) + '%)';
            }
        },
        credits: {enabled: false},
        series: [{
            name: "人数",
            data: seriesDataA1,
            size: '60%',
            innerSize: '55%'
        }]
    }, function (c) {
        if (c.title == null || c.title == undefined) {
            return;
        }
        var centerY = c.series[0].center[1],
            titleHeight = parseInt(c.title.styles.fontSize);
        c.setTitle({
            y: centerY + titleHeight / 2 - titleHeight / 8
        });
        chart = c;
    })
}

//插入柱状图
function loadHighChartsColumn() {
    $('#wlxcContent').highcharts({
        chart: {type: 'column', backgroundColor: 'rgba(0,0,0,0)'},
        title: {text: ''},
        xAxis: {
            labels: {style: {color: 'black'}},
            categories: ['矛盾纠纷', '消防安全', '城市卫生', '建筑违章']
        },
        yAxis: {
            title: {text: "个", align: 'high', style: {"color": "black"}},
            labels: {overflow: 'justify', style: {color: 'black'}},
            gridLineWidth: 1
        },
        plotOptions: {
            column: {
                dataLabels: {
                    enabled: true
                }
            }
        },
        legend: {
            itemStyle: {
                fontSize: 11
            },
            symbolPadding: 1,
            itemDistance: 10,
            align: 'right',
            verticalAlign: 'top'
        },
        tooltip: {valueSuffix: '个'},
        credits: {enabled: false},
        series: [{
            data: seriesDataB1,
            name: "已发现问题",
            color: '#58ACD6'
        }, {
            data: seriesDataB2,
            name: "已解决问题",
            color: '#67D66A'
        }]
    });
}

//插入条形图
function loadHighChartsBar() {
    $('#xxcjContent').highcharts({
        chart: {type: 'bar', backgroundColor: 'rgba(0,0,0,0)'},
        title: {text: ''},
        xAxis: {
            labels: {style: {color: 'black'}},
            categories: ['老年信息', '残疾人', '大龄失业', '低保信息', '失业人员',
                '双拥信息', '出生婴儿', '计生信息', '未成年']
        },
        yAxis: {
            title: {text: "采集数(个)", align: 'high', style: {"color": "black"}},
            labels: {overflow: 'justify', style: {color: 'black'}},
            gridLineWidth: 1
        },
        plotOptions: {
            bar: {
                dataLabels: {
                    enabled: true
                }
            }
        },
        legend: {
            enabled: false
        },
        tooltip: {valueSuffix: '个'},
        credits: {enabled: false},
        series: [{
            data: seriesDataC1,
            name: "采集数",
            color: '#0090D6'
        }]
    });
}



function showZdzfrqDiv(data) {
    $("#swnry").text(data.swnry);
    $("#jsbr").text(data.jsbr);
    $("#xdry").text(data.xdry);
    $("#swiry").text(data.swiry);
    $("#sqjz").text(data.sqjz);
}

//返回首页
function goBackToStaticIndex() {
    window.location = "statistics_index.html?fromGeneralInfo=1";
}

function Request(key) {
    var retValue = (window.location.search.match(new RegExp("(?:^\\?|&)" + key + "=(.*?)(?=&|$)")) || ['', null])[1];
    return (retValue == null ? "" : retValue);
}

function goWgDt() {
    window.location.href = "../wgMap/wgMap.html";
}
