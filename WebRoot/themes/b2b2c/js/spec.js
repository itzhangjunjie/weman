var Eop = Eop||{};
Eop.Spec={
	init:function(haveSpec){
		if(haveSpec==1){
			var self = this;
			this.refresh();
			$("a[name='goods_spec'][specvid!='']").click(function(){
				var link = $(this);
				if(link.attr("class")!='hovered' && link.attr("class")!='disabled' ){
					self.specClick($(this));
				}
				return false;
			});
			$("#goodsform [name='action']").val("addProduct");
		}else{
	//			canBuy();
	//			var pro =this.findProduct(product_ar);
			BtnTipRefresh.refresh(Products);
		}
	},
	specClick:function(specLink){
		specLink.parents("ul").find("a[specvid!='']").removeClass("hovered");
		specLink.parent().parent().parent().parent().find("em").addClass("checked");
		specLink.addClass("hovered");
 		
		this.refresh(specLink);
	},
	findGoodsImg:function(vid){
		for(i in  spec_imgs){
			var specimg = spec_imgs[i];
			if(specimg.specvid==parseInt(vid)){
				return specimg.goods_img;
			}
		}
	},
	
	//根据当前选择的规格找到货品
	findProduct:function(vidAr){
		var pros =[];
		//判断两个数组元素值是否相同，不判断位置情况
		function arraySame(ar1,ar2){
			//if(ar1.length!=ar2.length) return false;
			
			for(i in ar1){
				if($.inArray(ar1[i],ar2)==-1){ //不存在
					return false;
				}
			}
			return true;
		}
		
		var self = this;
	 
		for(i in Products){
			var product= Products[i];
			if(arraySame(vidAr,product.specs)){
				pros[pros.length] =product; 
			}
		}	
		 
		return pros;
	}
	,
	refresh:function(specLink){
		var self = this;
		var product_ar=[];
		$(".spec-item a.hovered").each(function(){
			var link = $(this);
			product_ar[product_ar.length]=parseInt(link.attr("specvid"));
		});
				
		var pro =this.findProduct(product_ar);
		for(i in Refresh){
			Refresh[i].refresh(pro,specLink,product_ar);
		}
		if(pro.length==1){
			$("strong[nctype='goods_stock']").html(pro[0].store);
			$("#productid").val(pro[0].product_id);
		}
	}

};
var StateRefresh={
	ArrrRemove:function( ar,obj) {  
		var new_ar =[];
		for( var i in ar ){
			if(obj!= ar[i]){
				new_ar.push(ar[i]);
			}
		}
		return new_ar;
	},

	refresh:function(pro,specLink,product_ar){
		//pro:找到的product [{sprc:{}},{}]
		//product_ar:选中的规格[1,2]
		
		var self  = this;
		if(product_ar.length>0){
		//从目前未选中的规格中循环
			$(".spec-item").not( specLink.parents(".spec-item") ).find("a").each(function(){
				var link = $(this);
				var proar=product_ar;
				link.parents(".spec-item").find("a").not(this).each(function(){
					var specvid = parseInt($(this).attr("specvid"));
					proar= self.ArrrRemove(proar,specvid);
				});
				
				var specvid = parseInt(link.attr("specvid"));
				proar.push(specvid);
				
				var result =Eop.Spec.findProduct(proar);
				if(!result || result.length==0){
					link.addClass("disabled");
				}else{
					link.removeClass("disabled");
				}
				proar.pop();
				
			});
		}
	}
};
var SelectTipRefresh={
	refresh:function(pro){
		var i=0;
		var specHtml="";
		$(".spec-item a.hovered").each(function(){
			if(i==0) specHtml="";
			if(i!=0) specHtml+="、";
			specHtml +=$(this).attr("title")+"";
			i++;
		});	
		if(i>0){
			specHtml="<dt>您已选择：</dt>"+"<dd><font color='red'>"+specHtml+"</font></dd>";
		}else{
			specHtml="<dt>请选择：</dt><dd>下列规格</dd>";
		}
		$(".spec-tip").html(specHtml);
	}
};
var PriceRefresh={
	refresh:function(pro){
		if(pro.length==1){
			$("#goods_price strong").text("￥"+pro[0].price );
			$("#productid").val(pro[0].product_id);
		}
		else{
			var maxPrice=0,minPrice=-1;
			for(i in pro){
				if( maxPrice<pro[i].price){
					maxPrice = pro[i].price;
				}
				if(minPrice==-1|| minPrice>pro[i].price){
					minPrice = pro[i].price;
				}
			}	
			$("#goods_price strong").text("￥"+minPrice+"-￥" +maxPrice);
		}
	}
};
function canBuy(){
	$("input[name=action]").val("addProduct");
	$("#buyNow").unbind('click');
	$("#buyNow").bind('click',function(){
		Goods.addToCart($(this));
	});
	
	$("#addCart").unbind('click');
	$("#addCart").bind('click',function(){
		Goods.addToCart($(this));
		return false;
	});
	
	$("#buyNow").css("cursor","pointer");
	$("#buyNow").tip({'disable':true});
	
	$("#addCart").css("cursor","pointer");
	$("#addCart").tip({'disable':true});
}

var BtnTipRefresh = {
	refresh:function(pro){
		$("#buyNow").attr('tip','');
		$("#addCart").attr('tip','');
		if(pro.length==1){
			if(pro[0].store==0){
				$("#goodsform .nc-key .nc-btn .btn-wrapper").hide();
				$("#goodsform .nc-key .nc-btn .store-not-enough").show();
			}else{
				canBuy();
				$("#goodsform .nc-key .nc-btn .btn-wrapper").show();
				$("#goodsform .nc-key .nc-btn .store-not-enough").hide();
			}
		}else{
			$("#buyNow").unbind('click');
			$("#buyNow").bind('click',function(){return false;});
			$("#buyNow").css("cursor","not-allowed");
			
			$("#addCart").unbind('click');
			$("#addCart").bind('click',function(){return false;});
			$("#addCart").css("cursor","not-allowed");
			var i=0;
			var tip='';
			$("#goodsform .spec-item em").each(function(){
				var em = $(this);
				
				if(em.attr("class")!='checked'){
					if(i!=0)tip+="、";
					tip+=em.text();
					i++;
				}
			});
			$("#addCart,#buyNow").tip({'disable':false,className:"cantbuy",text:"请选择:"+tip});
		}
	}	
};
var Refresh=[SelectTipRefresh,PriceRefresh,BtnTipRefresh,StateRefresh];

//tip插件
(function($) {
	$.fn.tip = function(options) {
		 
		var opts = $.extend({}, $.fn.tip.defaults, options);
		var tipEl= $(".tipbox");
		if(tipEl.size()==0){
			var html="<div class='tipbox' style='position: absolute;z-index:99'>";
			html+='<div class="tip-top"></div>';
			html+='<div class="tip">';
			html+='<div class="tip-text"></div>';
			html+='</div>';
			html+='<div class="tip-bottom"></div>';
			html+='</div>';
			tipEl=$(html).appendTo($("body"));
			tipEl.addClass(opts.className);
			tipEl.hide();
		}
		 tipEl.find(".tip>.tip-text").html(opts.text);
		 if( opts.disable){
			 $(this).unbind("mouseover").unbind("mousemove").unbind("mouseout");
		 }else{
			 $(this).bind("mouseover",function(e){
				 tipEl.show(); 
			 }).bind("mousemove",function(e){
				 tipEl.css('top',e.pageY+15).css('left',e.pageX+15);
			 }).bind("mouseout",function(){
				tipEl.hide();
			 });
		 }
	};
	
    $.fn.tip.defaults = {
    	className:"tip",
        text:"", 
        disable:false
    };
    
})(jQuery);



