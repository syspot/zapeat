PrimeFaces.locales['pt_BR'] = {
        closeText: 'Fechar',
        prevText: 'Anterior',
        nextText: 'Pr&oacute;ximo',
        currentText: 'Come&ccedil;o',
        monthNames: ['Janeiro','Fevereiro','Mar&ccedil;o','Abril','Maio','Junho','Julho','Agosto','Setembro','Outubro','Novembro','Dezembro'],
        monthNamesShort: ['Jan','Fev','Mar','Abr','Mai','Jun', 'Jul','Ago','Set','Out','Nov','Dez'],
        dayNames: ['Domingo','Segunda','Ter&ccedil;a','Quarta','Quinta','Sexta','S&aacute;bado'],
        dayNamesShort: ['Dom','Seg','Ter','Qua','Qui','Sex','S&aacute;b'],
        dayNamesMin: ['D','S','T','Q','Q','S','S'],
        weekHeader: 'Semana',
        firstDay: 1,
        isRTL: false,
        showMonthAfterYear: false,
        yearSuffix: '',
        timeOnlyTitle: 'S&oacute; Horas',
        timeText: 'Tempo',
        hourText: 'Hora',
        minuteText: 'Minuto',
        secondText: 'Segundo',
        currentText: 'Data Atual',
        ampm: false,
        month: 'M&ecirc;s',
        week: 'Semana',
        day: 'Dia',
        allDayText : 'Todo Dia'
    };

var win = null;

function NewWindow(mypage, myname, l, t, w, h, s, r) {
	settings = 'height=' + h + ',width=' + w + ',top=' + t + ',left=' + l
			+ ',scrollbars=' + s + ',resizable=' + r + ',status=no';
	win = window.open(mypage, myname, settings)
}

/*Função  Pai de Mascaras*/
function Mascara(o, f) {
	v_obj = o
	v_fun = f
	setTimeout("execmascara()", 1)
}

/*Função que Executa os objetos*/
function execmascara() {
	v_obj.value = v_fun(v_obj.value)
}

/*Função que Determina as expressões regulares dos objetos*/
function leech(v) {
	v = v.replace(/o/gi, "0")
	v = v.replace(/i/gi, "1")
	v = v.replace(/z/gi, "2")
	v = v.replace(/e/gi, "3")
	v = v.replace(/a/gi, "4")
	v = v.replace(/s/gi, "5")
	v = v.replace(/t/gi, "7")
	return v
}

/*Função que permite apenas numeros*/
function Integer(v) {
	return v.replace(/\D/g, "")
}

/*Função que padroniza telefone (11) 4184-1241*/
function Telefone(v) {
	v = v.replace(/\D/g, "")
	v = v.replace(/^(\d\d)(\d)/g, "($1) $2")
	v = v.replace(/(\d{4})(\d)/, "$1-$2")
	return v
}

/*Função que padroniza telefone (11) 41841241*/
function TelefoneCall(v) {
	v = v.replace(/\D/g, "")
	v = v.replace(/^(\d\d)(\d)/g, "($1) $2")
	return v
}

/*Função que padroniza CPF*/
function Cpf(v) {
	v = v.replace(/\D/g, "")
	v = v.replace(/(\d{3})(\d)/, "$1.$2")
	v = v.replace(/(\d{3})(\d)/, "$1.$2")

	v = v.replace(/(\d{3})(\d{1,2})$/, "$1-$2")
	return v
}

/*Função que padroniza CEP*/
function Cep(v) {
	v = v.replace(/D/g, "")
	v = v.replace(/^(\d{5})(\d)/, "$1-$2")
	return v
}

/*Função que padroniza CNPJ*/
function Cnpj(v) {
	v = v.replace(/\D/g, "")
	v = v.replace(/^(\d{2})(\d)/, "$1.$2")
	v = v.replace(/^(\d{2})\.(\d{3})(\d)/, "$1.$2.$3")
	v = v.replace(/\.(\d{3})(\d)/, ".$1/$2")
	v = v.replace(/(\d{4})(\d)/, "$1-$2")
	return v
}

/*Função que permite apenas numeros Romanos*/
function Romanos(v) {
	v = v.toUpperCase()
	v = v.replace(/[^IVXLCDM]/g, "")

	while (v.replace(
			/^M{0,4}(CM|CD|D?C{0,3})(XC|XL|L?X{0,3})(IX|IV|V?I{0,3})$/, "") != "")
		v = v.replace(/.$/, "")
	return v
}

/*Função que padroniza o Site*/
function Site(v) {
	v = v.replace(/^http:\/\/?/, "")
	dominio = v
	caminho = ""
	if (v.indexOf("/") > -1)
		dominio = v.split("/")[0]
	caminho = v.replace(/[^\/]*/, "")
	dominio = dominio.replace(/[^\w\.\+-:@]/g, "")
	caminho = caminho.replace(/[^\w\d\+-@:\?&=%\(\)\.]/g, "")
	caminho = caminho.replace(/([\?&])=/, "$1")
	if (caminho != "")
		dominio = dominio.replace(/\.+$/, "")
	v = "http://" + dominio + caminho
	return v
}

/*Função que padroniza DATA*/
function Data(v) {
	v = v.replace(/\D/g, "")
	v = v.replace(/(\d{2})(\d)/, "$1/$2")
	v = v.replace(/(\d{2})(\d)/, "$1/$2")
	return v
}

/*Função que padroniza DATA*/
function Hora(v) {
	v = v.replace(/\D/g, "")
	v = v.replace(/(\d{2})(\d)/, "$1:$2")
	return v
}

/*Função que padroniza valor monétario*/
function Valor(v) {
	v = v.replace(/\D/g, "") //Remove tudo o que não é dígito
	v = v.replace(/^([0-9]{3}\.?){3}-[0-9]{2}$/, "$1.$2");
	//v=v.replace(/(\d{3})(\d)/g,"$1,$2")
	v = v.replace(/(\d)(\d{2})$/, "$1.$2") //Coloca ponto antes dos 2 últimos digitos
	return v
}

/*Função que padroniza Area*/
function Area(v) {
	v = v.replace(/\D/g, "")
	v = v.replace(/(\d)(\d{2})$/, "$1.$2")
	return v

}

function mascaraMoeda(campo) {
	campo.value = MaskMonetario(campo.value);
}

function MaskMonetario(v) {
	v = v.replace(/\D/g, ""); //Remove tudo o que não é dígito
	v = v.replace(/(\d{2})$/, ",$1"); //Coloca a virgula
	v = v.replace(/(\d+)(\d{3},\d{2})$/g, "$1.$2"); //Coloca o primeiro ponto
	var qtdLoop = (v.length - 3) / 3;
	var count = 0;
	while (qtdLoop > count) {
		count++;
		v = v.replace(/(\d+)(\d{3}.*)/, "$1.$2"); //Coloca o resto dos pontos
	}
	v = v.replace(/^(0)(\d)/g, "$2"); //Coloca hífen entre o quarto e o quinto dígitos
	return v;
}

//Limita a quantidade de caracteres num textarea
function validarQtdeCaracteres(valor, max) {
	if (valor.length < max) {

		return true;

	}

	alert('Insira no máximo ' + max + ' caracteres.');

	window.event.returnValue = false;

	return false;
}

function MascaraMoeda(objTextBox, SeparadorMilesimo, SeparadorDecimal, e){
    var sep = 0;
    var key = "";
    var i = j = 0;
    var len = len2 = 0;
    var strCheck = '0123456789';
    var aux = aux2 = '';
    var whichCode = (window.Event) ? e.which : e.keyCode;
    if (whichCode == 13) return true;
    key = String.fromCharCode(whichCode); // Valor para o código da Chave
    if (strCheck.indexOf(key) == -1) return false; // Chave inválida
    len = objTextBox.value.length;
    for(i = 0; i < len; i++)
        if ((objTextBox.value.charAt(i) != '0') && (objTextBox.value.charAt(i) != SeparadorDecimal)) break;
    aux = '';
    for(; i < len; i++)
        if (strCheck.indexOf(objTextBox.value.charAt(i))!=-1) aux += objTextBox.value.charAt(i);
    aux += key;
    len = aux.length;
    if (len == 0) objTextBox.value = "";
    if (len == 1) objTextBox.value = "0"+ SeparadorDecimal + "0" + aux;
    if (len == 2) objTextBox.value = "0"+ SeparadorDecimal + aux;
    if (len > 2) {
        aux2 = "";
        for (j = 0, i = len - 3; i >= 0; i--) {
            if (j == 3) {
                aux2 += SeparadorMilesimo;
                j = 0;
            }
            aux2 += aux.charAt(i);
            j++;
        }
        objTextBox.value = "";
        len2 = aux2.length;
        for (i = len2 - 1; i >= 0; i--)
        objTextBox.value += aux2.charAt(i);
        objTextBox.value += SeparadorDecimal + aux.substr(len - 2, len);
    }
    return false;
}