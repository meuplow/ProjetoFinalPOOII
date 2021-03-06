package controle;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.mysql.cj.ParseInfo;

import dao.FormularioDAO;
import dao.UsuarioDAO;
import modelo.Formulario;
import visao.JanelaPrincipal;

public class FormularioControle implements ActionListener, MouseListener {

	private JanelaPrincipal janelaPrincipal;
	private FormularioDAO formularioDAO;
	private UsuarioControle usuarioControle;

	public FormularioControle(JanelaPrincipal janelaPrincipal, UsuarioControle usuarioControle) {
		this.janelaPrincipal = janelaPrincipal;
		this.usuarioControle = usuarioControle;
		this.formularioDAO = new FormularioDAO();
		this.janelaPrincipal.getButtonFazerReserva().addActionListener(this);
		this.janelaPrincipal.getButtonVerReservas().addActionListener(this);
		this.janelaPrincipal.getButtonVerificaQtd().addActionListener(this);
		this.janelaPrincipal.getButtonRevisar().addActionListener(this);
		this.janelaPrincipal.getButtonFinalizar().addActionListener(this);
		this.janelaPrincipal.getButtonApagarRequisicao1().addActionListener(this);
		this.janelaPrincipal.getButtonApagarRequisicao2().addActionListener(this);
		this.janelaPrincipal.getButtonApagarRequisicao3().addActionListener(this);
		this.janelaPrincipal.getButtonApagarRequisicao4().addActionListener(this);
		this.janelaPrincipal.getButtonVerRequisicao1().addActionListener(this);
		this.janelaPrincipal.getButtonVerRequisicao2().addActionListener(this);
		this.janelaPrincipal.getButtonVerRequisicao3().addActionListener(this);
		this.janelaPrincipal.getButtonVerRequisicao4().addActionListener(this);
		this.janelaPrincipal.getButtonFecharPainelAbrirFormulario().addActionListener(this);
		this.janelaPrincipal.getLabelAvancarPainelInicioFormulario().addMouseListener(this);
		this.janelaPrincipal.getLabelVoltarPainelInicioFormulario().addMouseListener(this);
		this.janelaPrincipal.getLabelVoltarPainelData().addMouseListener(this);
		this.janelaPrincipal.getLabelAvancarPainelData().addMouseListener(this);
		this.janelaPrincipal.getLabelVoltarEquipamentos().addMouseListener(this);
		this.janelaPrincipal.getLabelAvancarEquipamentos().addMouseListener(this);
		this.janelaPrincipal.getLabelVoltarVerReserva().addMouseListener(this);
	}

	// Me'todo que salva uma requisicao de reserva de sala
	public void salvarRequisicao() {
		String solicitante, telefone, email, atividade, modalidade, curso, equipamentos = "";
		String[] data = new String[6];
		int numeroAlunos;
		int[] horaInicial = new int[6], horaFinal = new int[6];
		Formulario formulario;

		// Varia'vel usada para dar um sinal que uma reserva foi inserida
		// int sinal = 0;

		// Formatador da data recebida da tela
		DateFormat dataFormatada = new SimpleDateFormat("yyyy-MM-dd");

		// Quantidade de datas selecionadas
		int quantidadeDias;
		quantidadeDias = this.janelaPrincipal.getComboBoxQtdDias().getSelectedIndex() + 1;

		// Indice para auxiliar na atribuicao de valores das datas
		int i;

		// Atribuicao de valor para varia'veis com dados recebidos da tela
		solicitante = this.janelaPrincipal.getFieldSolicitante().getText();
		telefone = this.janelaPrincipal.getFieldTelefone().getText();
		email = this.janelaPrincipal.getFieldEmail().getText();
		atividade = this.janelaPrincipal.getButtonGroupAtividade().getSelection().getActionCommand();
		modalidade = this.janelaPrincipal.getComboBoxModalidade().getSelectedItem().toString();
		curso = this.janelaPrincipal.getComboBoxCurso().getSelectedItem().toString();
		numeroAlunos = Integer.parseInt(this.janelaPrincipal.getFieldNumeroAlunos().getText());

		// Passagem de valor de um checkBox para a varia'vel
		if (this.janelaPrincipal.getCheckBoxEquipamentoDeAudio().isSelected()) {
			equipamentos = equipamentos + this.janelaPrincipal.getCheckBoxEquipamentoDeAudio().getText();
		}

		if (this.janelaPrincipal.getCheckBoxEquipamentoDvd().isSelected()) {
			equipamentos = equipamentos + this.janelaPrincipal.getCheckBoxEquipamentoDvd().getText();
		}

		if (this.janelaPrincipal.getCheckBoxProjetorDeSlides().isSelected()) {
			equipamentos = equipamentos + this.janelaPrincipal.getCheckBoxProjetorDeSlides().getText();
		}

		if (this.janelaPrincipal.getCheckBoxRetroprojetor().isSelected()) {
			equipamentos = equipamentos + this.janelaPrincipal.getCheckBoxRetroprojetor().getText();
		}

		if (this.janelaPrincipal.getCheckBoxOutroEspecificar().isSelected()) {
			equipamentos = equipamentos + this.janelaPrincipal.getFieldEspecificacao().getText();
		}

		// Passagem das datas (dia, hora inicial e final)
		i = 0;
		if (i < quantidadeDias) {
			data[i] = dataFormatada.format(this.janelaPrincipal.getDataPanelDia1().getDate());
			horaInicial[0] = this.janelaPrincipal.getComboBoxInicioDia1().getSelectedIndex() + 7;
			horaFinal[0] = this.janelaPrincipal.getComboBoxFimDia1().getSelectedIndex() + 8;
		}

		i++;

		if (i < quantidadeDias) {
			data[1] = dataFormatada.format(this.janelaPrincipal.getDataPanelDia2().getDate());
			horaInicial[1] = this.janelaPrincipal.getComboBoxInicioDia2().getSelectedIndex() + 7;
			horaFinal[1] = this.janelaPrincipal.getComboBoxFimDia2().getSelectedIndex() + 8;
		}

		i++;

		if (i < quantidadeDias) {
			data[2] = dataFormatada.format(this.janelaPrincipal.getDataPanelDia3().getDate());
			horaInicial[2] = this.janelaPrincipal.getComboBoxInicioDia3().getSelectedIndex() + 7;
			horaFinal[2] = this.janelaPrincipal.getComboBoxFimDia3().getSelectedIndex() + 8;
		}

		i++;

		if (i < quantidadeDias) {
			data[3] = dataFormatada.format(this.janelaPrincipal.getDataPanelDia4().getDate());
			horaInicial[3] = this.janelaPrincipal.getComboBoxInicioDia4().getSelectedIndex() + 7;
			horaFinal[3] = this.janelaPrincipal.getComboBoxFimDia4().getSelectedIndex() + 8;
		}

		i++;

		if (i < quantidadeDias) {
			data[4] = dataFormatada.format(this.janelaPrincipal.getDataPanelDia5().getDate());
			horaInicial[4] = this.janelaPrincipal.getComboBoxInicioDia5().getSelectedIndex() + 7;
			horaFinal[4] = this.janelaPrincipal.getComboBoxFimDia5().getSelectedIndex() + 8;
		}

		i++;

		if (i < quantidadeDias) {
			data[5] = dataFormatada.format(this.janelaPrincipal.getDataPanelDia6().getDate());
			horaInicial[5] = this.janelaPrincipal.getComboBoxInicioDia6().getSelectedIndex() + 7;
			horaFinal[5] = this.janelaPrincipal.getComboBoxFimDia6().getSelectedIndex() + 8;
		}

		i = 0;

		formulario = new Formulario(solicitante, telefone, email, numeroAlunos, atividade, modalidade, curso,
				equipamentos, data, horaInicial, horaFinal);

		if (validarCamposSolicitante() == true && validarCamposEquipamentos() == true && validarCamposData() == true) {

			boolean insercao = formularioDAO.inserirRequisicao(formulario, usuarioControle.getUsuarioId());

			if (insercao) {
				limparCampos();

				// Id da u'ltima requisicao inserida e' salvo
				int requisicaoId = formularioDAO.retornaFormularioId(usuarioControle.getUsuarioId());

				// Insercao na tabela reserva das datas da requisicao
				for (int j = 0; j < quantidadeDias; j++) {

					boolean reserva = formularioDAO.inserirReserva(requisicaoId, formulario.getData(j),
							formulario.getHoraInicial(j), formulario.getHoraFinal(j));
					if (reserva == false) {
						this.janelaPrincipal.mensagemRequisicaoErro();
					}

				}

				this.janelaPrincipal.mensagemRequisicaoSucesso();

			} else {
				this.janelaPrincipal.mensagemRequisicaoErro();
			}

		} else {
			this.janelaPrincipal.mensagemCamposVazios();
		}

	} // salvarRequisicao() 

	public void exibirRequisicoes() {
		int idRequisicao[] = formularioDAO.retornaFormularioIdGeral(usuarioControle.getUsuarioId());

		if (idRequisicao[0] > 0) {
			this.janelaPrincipal.getLabelRequisicao1().setText("#R" + idRequisicao[0]);
			this.janelaPrincipal.getLabelRequisicao1().setVisible(true);
			this.janelaPrincipal.getButtonVerRequisicao1().setVisible(true);
			this.janelaPrincipal.getButtonApagarRequisicao1().setVisible(true);
		} else {
			this.janelaPrincipal.getLabelRequisicao1().setVisible(false);
			this.janelaPrincipal.getButtonVerRequisicao1().setVisible(false);
			this.janelaPrincipal.getButtonApagarRequisicao1().setVisible(false);
		}

		if (idRequisicao[1] > 0) {
			this.janelaPrincipal.getLabelRequisicao2().setText("#R" + idRequisicao[1]);
			this.janelaPrincipal.getLabelRequisicao2().setVisible(true);
			this.janelaPrincipal.getButtonVerRequisicao2().setVisible(true);
			this.janelaPrincipal.getButtonApagarRequisicao2().setVisible(true);
		} else {
			this.janelaPrincipal.getLabelRequisicao2().setVisible(false);
			this.janelaPrincipal.getButtonVerRequisicao2().setVisible(false);
			this.janelaPrincipal.getButtonApagarRequisicao2().setVisible(false);
		}

		if (idRequisicao[2] > 0) {
			this.janelaPrincipal.getLabelRequisicao3().setText("#R" + idRequisicao[2]);
			this.janelaPrincipal.getLabelRequisicao3().setVisible(true);
			this.janelaPrincipal.getButtonVerRequisicao3().setVisible(true);
			this.janelaPrincipal.getButtonApagarRequisicao3().setVisible(true);
		} else {
			this.janelaPrincipal.getLabelRequisicao3().setVisible(false);
			this.janelaPrincipal.getButtonVerRequisicao3().setVisible(false);
			this.janelaPrincipal.getButtonApagarRequisicao3().setVisible(false);
		}

		if (idRequisicao[3] > 0) {
			this.janelaPrincipal.getLabelRequisicao4().setText("#R" + idRequisicao[3]);
			this.janelaPrincipal.getLabelRequisicao4().setVisible(true);
			this.janelaPrincipal.getButtonVerRequisicao4().setVisible(true);
			this.janelaPrincipal.getButtonApagarRequisicao4().setVisible(true);
		} else {
			this.janelaPrincipal.getLabelRequisicao4().setVisible(false);
			this.janelaPrincipal.getButtonVerRequisicao4().setVisible(false);
			this.janelaPrincipal.getButtonApagarRequisicao4().setVisible(false);
		}

	} // exibirRequisicoes()

	// Me'todo que exclui uma requisicao
	public void excluirRequisicao(int i) {
		int idRequisicao[] = formularioDAO.retornaFormularioIdGeral(usuarioControle.getUsuarioId());
		int id = idRequisicao[i];
		if (id > 0) {
			if (formularioDAO.excluirRequisicao(id)) {
				this.janelaPrincipal.mensagemExcluirRequisicaoSucesso();
			} else {
				this.janelaPrincipal.mensagemExcluirRequisicaoErro();
			}
		}
	} // excluirRequisicao()

	// Me'todo que troca o painel exibido
	public void trocarPainel(String nomePainel) {
		this.janelaPrincipal.trocarPainel(nomePainel);
	}

	// Me'todo que valida os campos da tela inicial do questiona'rio
	public boolean validarCamposSolicitante() {	
		if (this.janelaPrincipal.getFieldTelefone().getText().isEmpty()
				|| this.janelaPrincipal.getFieldEmail().getText().isEmpty()
				|| this.janelaPrincipal.getFieldNumeroAlunos().getText().isEmpty()
				|| this.janelaPrincipal.getFieldSolicitante().getText().isEmpty()
				|| this.janelaPrincipal.getComboBoxModalidade().getSelectedIndex() == -1
				|| this.janelaPrincipal.getComboBoxCurso().getSelectedIndex() == -1
				|| this.janelaPrincipal.getButtonGroupAtividade().isSelected(null)) {
			return false;
		}
		
		try {
			int numeroAlunos;
			numeroAlunos = Integer.parseInt(this.janelaPrincipal.getFieldNumeroAlunos().getText()); 
			return true;
		} catch(Exception e) {
			return false;
		}
	} // validarCamposSolicitante()

	// Me'todo que valida os campos de data do formula'rio
	public boolean validarCamposData() {
		int contador = 0, quantidadeDias = 0, aux = 0;
		Date dataAtual = new Date();
		Date dataInserida;

		// Pega a quantidade de dias informada pelo usua'rio se nao for nula
		if (this.janelaPrincipal.getComboBoxQtdDias().getSelectedIndex() >= 0) {
			quantidadeDias = this.janelaPrincipal.getComboBoxQtdDias().getSelectedIndex() + 1;
		}

		// Teste para checkar se ha' campos nao preenchidos
		if (!this.janelaPrincipal.getDataPanelDia1().toString().isEmpty()
				&& !this.janelaPrincipal.getDataPanelDia1().toString().equals("")
				&& this.janelaPrincipal.getComboBoxInicioDia1().getSelectedIndex() != -1
				&& this.janelaPrincipal.getComboBoxFimDia1().getSelectedIndex() != -1) {

			// Teste para checkar se data inserida e' maior que a data atual
			dataInserida = this.janelaPrincipal.getDataPanelDia1().getDate();
			if (dataAtual.compareTo(dataInserida) < 0) {
				contador++;
			}

		}

		if (!this.janelaPrincipal.getDataPanelDia2().toString().isEmpty()
				&& !this.janelaPrincipal.getDataPanelDia2().toString().equals("")
				&& this.janelaPrincipal.getComboBoxInicioDia2().getSelectedIndex() != -1
				&& this.janelaPrincipal.getComboBoxFimDia2().getSelectedIndex() != -1) {

			dataInserida = this.janelaPrincipal.getDataPanelDia2().getDate();
			if (dataAtual.compareTo(dataInserida) < 0) {
				contador++;
			}

		}
		
		if (!this.janelaPrincipal.getDataPanelDia3().toString().isEmpty()
				&& !this.janelaPrincipal.getDataPanelDia3().toString().equals("")
				&& this.janelaPrincipal.getComboBoxInicioDia3().getSelectedIndex() != -1
				&& this.janelaPrincipal.getComboBoxFimDia3().getSelectedIndex() != -1) {

			dataInserida = this.janelaPrincipal.getDataPanelDia3().getDate();
			if (dataAtual.compareTo(dataInserida) < 0) {
				contador++;
			}

		}
		
		if (!this.janelaPrincipal.getDataPanelDia4().toString().isEmpty()
				&& !this.janelaPrincipal.getDataPanelDia4().toString().equals("")
				&& this.janelaPrincipal.getComboBoxInicioDia4().getSelectedIndex() != -1
				&& this.janelaPrincipal.getComboBoxFimDia4().getSelectedIndex() != -1) {

			dataInserida = this.janelaPrincipal.getDataPanelDia4().getDate();
			if (dataAtual.compareTo(dataInserida) < 0) {
				contador++;
			}

		}
		
		if (!this.janelaPrincipal.getDataPanelDia5().toString().isEmpty()
				&& !this.janelaPrincipal.getDataPanelDia5().toString().equals("")
				&& this.janelaPrincipal.getComboBoxInicioDia5().getSelectedIndex() != -1
				&& this.janelaPrincipal.getComboBoxFimDia5().getSelectedIndex() != -1) {

			dataInserida = this.janelaPrincipal.getDataPanelDia5().getDate();
			if (dataAtual.compareTo(dataInserida) < 0) {
				contador++;
			}

		}
		
		if (!this.janelaPrincipal.getDataPanelDia6().toString().isEmpty()
				&& !this.janelaPrincipal.getDataPanelDia6().toString().equals("")
				&& this.janelaPrincipal.getComboBoxInicioDia6().getSelectedIndex() != -1
				&& this.janelaPrincipal.getComboBoxFimDia6().getSelectedIndex() != -1) {

			dataInserida = this.janelaPrincipal.getDataPanelDia6().getDate();
			if (dataAtual.compareTo(dataInserida) < 0) {
				contador++;
			}

		}

		// Teste para checkar se hora inicial > hora final
		if (this.janelaPrincipal.getComboBoxInicioDia1().getSelectedIndex()
				+ 7 >= this.janelaPrincipal.getComboBoxFimDia1().getSelectedIndex() + 8) {
			aux++;
		}
		
		if (this.janelaPrincipal.getComboBoxInicioDia2().getSelectedIndex()
				+ 7 >= this.janelaPrincipal.getComboBoxFimDia2().getSelectedIndex() + 8) {
			aux++;
		}
		
		if (this.janelaPrincipal.getComboBoxInicioDia3().getSelectedIndex()
				+ 7 >= this.janelaPrincipal.getComboBoxFimDia3().getSelectedIndex() + 8) {
			aux++;
		}
		
		if (this.janelaPrincipal.getComboBoxInicioDia4().getSelectedIndex()
				+ 7 >= this.janelaPrincipal.getComboBoxFimDia4().getSelectedIndex() + 8) {
			aux++;
		}
		
		if (this.janelaPrincipal.getComboBoxInicioDia5().getSelectedIndex()
				+ 7 >= this.janelaPrincipal.getComboBoxFimDia5().getSelectedIndex() + 8) {
			aux++;
		}
		
		if (this.janelaPrincipal.getComboBoxInicioDia6().getSelectedIndex()
				+ 7 >= this.janelaPrincipal.getComboBoxFimDia6().getSelectedIndex() + 8) {
			aux++;
		}

		// Retorna falso se em alguma das linhas o horario inicial e' maior ou igual que o horario final
		if (aux > 0) {
			return false;
		}

		// Retorna falso se a quantidades de dias selecionada pelo usua'rio e' igual a quantidade de dados capturados
		if (contador != quantidadeDias) {
			return false;
		}

		return true;
	} // validarCamposData()

	// Me'todo que valida os campos da tela de equipamentos
	public boolean validarCamposEquipamentos() {

		if (this.janelaPrincipal.getCheckBoxOutroEspecificar().isSelected()
				&& this.janelaPrincipal.getFieldEspecificacao().getText().isEmpty())

		{
			this.janelaPrincipal.mensagemCamposVazios();
			return false;
		}
		return true;
		
	} // validarCamposEquipamentos()

	// Me'todo que limpa os campos das telas
	public void limparCampos() {
		this.janelaPrincipal.getFieldTelefone().setText("");
		this.janelaPrincipal.getFieldEmail().setText("");
		this.janelaPrincipal.getFieldNumeroAlunos().setText("");
		this.janelaPrincipal.getFieldSolicitante().setText("");
		this.janelaPrincipal.getRadioButtonAula().setSelected(false);
		this.janelaPrincipal.getRadioButtonProva().setSelected(false);
		this.janelaPrincipal.getRadioButtonSeminario().setSelected(false);
		this.janelaPrincipal.getComboBoxModalidade().setSelectedIndex(-1);
		this.janelaPrincipal.getComboBoxCurso().setSelectedIndex(-1);
		this.janelaPrincipal.getComboBoxQtdDias().setSelectedIndex(-1);
		this.janelaPrincipal.getComboBoxFimDia1().setSelectedIndex(-1);
		this.janelaPrincipal.getComboBoxFimDia2().setSelectedIndex(-1);
		this.janelaPrincipal.getComboBoxFimDia3().setSelectedIndex(-1);
		this.janelaPrincipal.getComboBoxFimDia4().setSelectedIndex(-1);
		this.janelaPrincipal.getComboBoxFimDia5().setSelectedIndex(-1);
		this.janelaPrincipal.getComboBoxFimDia6().setSelectedIndex(-1);
		this.janelaPrincipal.getComboBoxInicioDia1().setSelectedIndex(-1);
		this.janelaPrincipal.getComboBoxInicioDia2().setSelectedIndex(-1);
		this.janelaPrincipal.getComboBoxInicioDia3().setSelectedIndex(-1);
		this.janelaPrincipal.getComboBoxInicioDia4().setSelectedIndex(-1);
		this.janelaPrincipal.getComboBoxInicioDia5().setSelectedIndex(-1);
		this.janelaPrincipal.getComboBoxInicioDia6().setSelectedIndex(-1);
		this.janelaPrincipal.getCheckBoxEquipamentoDeAudio().setSelected(false);
		this.janelaPrincipal.getCheckBoxEquipamentoDvd().setSelected(false);
		this.janelaPrincipal.getCheckBoxOutroEspecificar().setSelected(false);
		this.janelaPrincipal.getCheckBoxProjetorDeSlides().setSelected(false);
		this.janelaPrincipal.getCheckBoxRetroprojetor().setSelected(false);
		this.janelaPrincipal.getFieldEspecificacao().setText("");
		this.janelaPrincipal.getButtonGroupAtividade().clearSelection();
		this.janelaPrincipal.getDataPanelDia1().setCalendar(null);
		this.janelaPrincipal.getDataPanelDia2().setCalendar(null);
		this.janelaPrincipal.getDataPanelDia3().setCalendar(null);
		this.janelaPrincipal.getDataPanelDia4().setCalendar(null);
		this.janelaPrincipal.getDataPanelDia5().setCalendar(null);
		this.janelaPrincipal.getDataPanelDia6().setCalendar(null);
	}

	// Me'todo que altera a visibilidade dos campos de data na tela PainelData
	public void mostrarCamposDatas() {
		if (this.janelaPrincipal.getComboBoxQtdDias().getSelectedItem().equals("1 Dia")) {
			this.janelaPrincipal.getComboBoxInicioDia1().setVisible(true);
			this.janelaPrincipal.getComboBoxInicioDia1().setEnabled(true);
			this.janelaPrincipal.getComboBoxInicioDia2().setVisible(false);
			this.janelaPrincipal.getComboBoxInicioDia2().setEnabled(false);
			this.janelaPrincipal.getComboBoxInicioDia2().setSelectedIndex(-1);
			this.janelaPrincipal.getComboBoxInicioDia3().setVisible(false);
			this.janelaPrincipal.getComboBoxInicioDia3().setEnabled(false);
			this.janelaPrincipal.getComboBoxInicioDia3().setSelectedIndex(-1);
			this.janelaPrincipal.getComboBoxInicioDia4().setVisible(false);
			this.janelaPrincipal.getComboBoxInicioDia4().setEnabled(false);
			this.janelaPrincipal.getComboBoxInicioDia4().setSelectedIndex(-1);
			this.janelaPrincipal.getComboBoxInicioDia5().setVisible(false);
			this.janelaPrincipal.getComboBoxInicioDia5().setEnabled(false);
			this.janelaPrincipal.getComboBoxInicioDia5().setSelectedIndex(-1);
			this.janelaPrincipal.getComboBoxInicioDia6().setVisible(false);
			this.janelaPrincipal.getComboBoxInicioDia6().setEnabled(false);
			this.janelaPrincipal.getComboBoxInicioDia6().setSelectedIndex(-1);
			this.janelaPrincipal.getDataPanelDia1().setVisible(true);
			this.janelaPrincipal.getDataPanelDia1().setEnabled(true);
			this.janelaPrincipal.getDataPanelDia2().setVisible(false);
			this.janelaPrincipal.getDataPanelDia2().setEnabled(false);
			this.janelaPrincipal.getDataPanelDia2().getDateEditor().setDate(null);
			this.janelaPrincipal.getDataPanelDia3().setVisible(false);
			this.janelaPrincipal.getDataPanelDia3().setEnabled(false);
			this.janelaPrincipal.getDataPanelDia3().getDateEditor().setDate(null);
			this.janelaPrincipal.getDataPanelDia4().setVisible(false);
			this.janelaPrincipal.getDataPanelDia4().setEnabled(false);
			this.janelaPrincipal.getDataPanelDia4().getDateEditor().setDate(null);
			this.janelaPrincipal.getDataPanelDia5().setVisible(false);
			this.janelaPrincipal.getDataPanelDia5().setEnabled(false);
			this.janelaPrincipal.getDataPanelDia5().getDateEditor().setDate(null);
			this.janelaPrincipal.getDataPanelDia6().setVisible(false);
			this.janelaPrincipal.getDataPanelDia6().setEnabled(false);
			this.janelaPrincipal.getDataPanelDia6().getDateEditor().setDate(null);
			this.janelaPrincipal.getComboBoxFimDia1().setVisible(true);
			this.janelaPrincipal.getComboBoxFimDia1().setEnabled(true);
			this.janelaPrincipal.getComboBoxFimDia2().setVisible(false);
			this.janelaPrincipal.getComboBoxFimDia2().setEnabled(false);
			this.janelaPrincipal.getComboBoxFimDia2().setSelectedIndex(-1);
			this.janelaPrincipal.getComboBoxFimDia3().setVisible(false);
			this.janelaPrincipal.getComboBoxFimDia3().setEnabled(false);
			this.janelaPrincipal.getComboBoxFimDia3().setSelectedIndex(-1);
			this.janelaPrincipal.getComboBoxFimDia4().setVisible(false);
			this.janelaPrincipal.getComboBoxFimDia4().setEnabled(false);
			this.janelaPrincipal.getComboBoxFimDia4().setSelectedIndex(-1);
			this.janelaPrincipal.getComboBoxFimDia5().setVisible(false);
			this.janelaPrincipal.getComboBoxFimDia5().setEnabled(false);
			this.janelaPrincipal.getComboBoxFimDia5().setSelectedIndex(-1);
			this.janelaPrincipal.getComboBoxFimDia6().setVisible(false);
			this.janelaPrincipal.getComboBoxFimDia6().setEnabled(false);
			this.janelaPrincipal.getComboBoxFimDia6().setSelectedIndex(-1);
		}

		else if (this.janelaPrincipal.getComboBoxQtdDias().getSelectedItem().equals("2 Dias")) {

			this.janelaPrincipal.getComboBoxInicioDia1().setVisible(true);
			this.janelaPrincipal.getComboBoxInicioDia1().setEnabled(true);
			this.janelaPrincipal.getComboBoxInicioDia2().setVisible(true);
			this.janelaPrincipal.getComboBoxInicioDia2().setEnabled(true);
			this.janelaPrincipal.getComboBoxInicioDia3().setVisible(false);
			this.janelaPrincipal.getComboBoxInicioDia3().setEnabled(false);
			this.janelaPrincipal.getComboBoxInicioDia3().setSelectedIndex(-1);
			this.janelaPrincipal.getComboBoxInicioDia4().setVisible(false);
			this.janelaPrincipal.getComboBoxInicioDia4().setEnabled(false);
			this.janelaPrincipal.getComboBoxInicioDia4().setSelectedIndex(-1);
			this.janelaPrincipal.getComboBoxInicioDia5().setVisible(false);
			this.janelaPrincipal.getComboBoxInicioDia5().setEnabled(false);
			this.janelaPrincipal.getComboBoxInicioDia5().setSelectedIndex(-1);
			this.janelaPrincipal.getComboBoxInicioDia6().setVisible(false);
			this.janelaPrincipal.getComboBoxInicioDia6().setEnabled(false);
			this.janelaPrincipal.getComboBoxInicioDia6().setSelectedIndex(-1);
			this.janelaPrincipal.getDataPanelDia1().setVisible(true);
			this.janelaPrincipal.getDataPanelDia1().setEnabled(true);
			this.janelaPrincipal.getDataPanelDia2().setVisible(true);
			this.janelaPrincipal.getDataPanelDia2().setEnabled(true);
			this.janelaPrincipal.getDataPanelDia3().setVisible(false);
			this.janelaPrincipal.getDataPanelDia3().setEnabled(false);
			this.janelaPrincipal.getDataPanelDia3().getDateEditor().setDate(null);
			this.janelaPrincipal.getDataPanelDia4().setVisible(false);
			this.janelaPrincipal.getDataPanelDia4().setEnabled(false);
			this.janelaPrincipal.getDataPanelDia4().getDateEditor().setDate(null);
			this.janelaPrincipal.getDataPanelDia5().setVisible(false);
			this.janelaPrincipal.getDataPanelDia5().setEnabled(false);
			this.janelaPrincipal.getDataPanelDia5().getDateEditor().setDate(null);
			this.janelaPrincipal.getDataPanelDia6().setVisible(false);
			this.janelaPrincipal.getDataPanelDia6().setEnabled(false);
			this.janelaPrincipal.getDataPanelDia6().getDateEditor().setDate(null);
			this.janelaPrincipal.getComboBoxFimDia1().setVisible(true);
			this.janelaPrincipal.getComboBoxFimDia1().setEnabled(true);
			this.janelaPrincipal.getComboBoxFimDia2().setVisible(true);
			this.janelaPrincipal.getComboBoxFimDia2().setEnabled(true);
			this.janelaPrincipal.getComboBoxFimDia3().setVisible(false);
			this.janelaPrincipal.getComboBoxFimDia3().setEnabled(false);
			this.janelaPrincipal.getComboBoxFimDia3().setSelectedIndex(-1);
			this.janelaPrincipal.getComboBoxFimDia4().setVisible(false);
			this.janelaPrincipal.getComboBoxFimDia4().setEnabled(false);
			this.janelaPrincipal.getComboBoxFimDia4().setSelectedIndex(-1);
			this.janelaPrincipal.getComboBoxFimDia5().setVisible(false);
			this.janelaPrincipal.getComboBoxFimDia5().setEnabled(false);
			this.janelaPrincipal.getComboBoxFimDia5().setSelectedIndex(-1);
			this.janelaPrincipal.getComboBoxFimDia6().setVisible(false);
			this.janelaPrincipal.getComboBoxFimDia6().setEnabled(false);
			this.janelaPrincipal.getComboBoxFimDia6().setSelectedIndex(-1);
		}

		else if (this.janelaPrincipal.getComboBoxQtdDias().getSelectedItem().equals("3 Dias")) {

			this.janelaPrincipal.getComboBoxInicioDia1().setVisible(true);
			this.janelaPrincipal.getComboBoxInicioDia1().setEnabled(true);
			this.janelaPrincipal.getComboBoxInicioDia2().setVisible(true);
			this.janelaPrincipal.getComboBoxInicioDia2().setEnabled(true);
			this.janelaPrincipal.getComboBoxInicioDia3().setVisible(true);
			this.janelaPrincipal.getComboBoxInicioDia3().setEnabled(true);
			this.janelaPrincipal.getComboBoxInicioDia4().setVisible(false);
			this.janelaPrincipal.getComboBoxInicioDia4().setEnabled(false);
			this.janelaPrincipal.getComboBoxInicioDia4().setSelectedIndex(-1);
			this.janelaPrincipal.getComboBoxInicioDia5().setVisible(false);
			this.janelaPrincipal.getComboBoxInicioDia5().setEnabled(false);
			this.janelaPrincipal.getComboBoxInicioDia5().setSelectedIndex(-1);
			this.janelaPrincipal.getComboBoxInicioDia6().setVisible(false);
			this.janelaPrincipal.getComboBoxInicioDia6().setEnabled(false);
			this.janelaPrincipal.getComboBoxInicioDia6().setSelectedIndex(-1);
			this.janelaPrincipal.getDataPanelDia1().setVisible(true);
			this.janelaPrincipal.getDataPanelDia1().setEnabled(true);
			this.janelaPrincipal.getDataPanelDia2().setVisible(true);
			this.janelaPrincipal.getDataPanelDia2().setEnabled(true);
			this.janelaPrincipal.getDataPanelDia3().setVisible(true);
			this.janelaPrincipal.getDataPanelDia3().setEnabled(true);
			this.janelaPrincipal.getDataPanelDia4().setVisible(false);
			this.janelaPrincipal.getDataPanelDia4().setEnabled(false);
			this.janelaPrincipal.getDataPanelDia4().getDateEditor().setDate(null);
			this.janelaPrincipal.getDataPanelDia5().setVisible(false);
			this.janelaPrincipal.getDataPanelDia5().setEnabled(false);
			this.janelaPrincipal.getDataPanelDia5().getDateEditor().setDate(null);
			this.janelaPrincipal.getDataPanelDia6().setVisible(false);
			this.janelaPrincipal.getDataPanelDia6().setEnabled(false);
			this.janelaPrincipal.getDataPanelDia6().getDateEditor().setDate(null);
			this.janelaPrincipal.getComboBoxFimDia1().setVisible(true);
			this.janelaPrincipal.getComboBoxFimDia1().setEnabled(true);
			this.janelaPrincipal.getComboBoxFimDia2().setVisible(true);
			this.janelaPrincipal.getComboBoxFimDia2().setEnabled(true);
			this.janelaPrincipal.getComboBoxFimDia3().setVisible(true);
			this.janelaPrincipal.getComboBoxFimDia3().setEnabled(true);
			this.janelaPrincipal.getComboBoxFimDia4().setVisible(false);
			this.janelaPrincipal.getComboBoxFimDia4().setEnabled(false);
			this.janelaPrincipal.getComboBoxFimDia4().setSelectedIndex(-1);
			this.janelaPrincipal.getComboBoxFimDia5().setVisible(false);
			this.janelaPrincipal.getComboBoxFimDia5().setEnabled(false);
			this.janelaPrincipal.getComboBoxFimDia5().setSelectedIndex(-1);
			this.janelaPrincipal.getComboBoxFimDia6().setVisible(false);
			this.janelaPrincipal.getComboBoxFimDia6().setEnabled(false);
			this.janelaPrincipal.getComboBoxFimDia6().setSelectedIndex(-1);

		} else if (this.janelaPrincipal.getComboBoxQtdDias().getSelectedItem().equals("4 Dias")) {

			this.janelaPrincipal.getComboBoxInicioDia1().setVisible(true);
			this.janelaPrincipal.getComboBoxInicioDia1().setEnabled(true);
			this.janelaPrincipal.getComboBoxInicioDia2().setVisible(true);
			this.janelaPrincipal.getComboBoxInicioDia2().setEnabled(true);
			this.janelaPrincipal.getComboBoxInicioDia3().setVisible(true);
			this.janelaPrincipal.getComboBoxInicioDia3().setEnabled(true);
			this.janelaPrincipal.getComboBoxInicioDia4().setVisible(true);
			this.janelaPrincipal.getComboBoxInicioDia4().setEnabled(true);
			this.janelaPrincipal.getComboBoxInicioDia5().setVisible(false);
			this.janelaPrincipal.getComboBoxInicioDia5().setEnabled(false);
			this.janelaPrincipal.getComboBoxInicioDia5().setSelectedIndex(-1);
			this.janelaPrincipal.getComboBoxInicioDia6().setVisible(false);
			this.janelaPrincipal.getComboBoxInicioDia6().setEnabled(false);
			this.janelaPrincipal.getComboBoxInicioDia6().setSelectedIndex(-1);
			this.janelaPrincipal.getDataPanelDia1().setVisible(true);
			this.janelaPrincipal.getDataPanelDia1().setEnabled(true);
			this.janelaPrincipal.getDataPanelDia2().setVisible(true);
			this.janelaPrincipal.getDataPanelDia2().setEnabled(true);
			this.janelaPrincipal.getDataPanelDia3().setVisible(true);
			this.janelaPrincipal.getDataPanelDia3().setEnabled(true);
			this.janelaPrincipal.getDataPanelDia4().setVisible(true);
			this.janelaPrincipal.getDataPanelDia4().setEnabled(true);
			this.janelaPrincipal.getDataPanelDia5().setVisible(false);
			this.janelaPrincipal.getDataPanelDia5().setEnabled(false);
			this.janelaPrincipal.getDataPanelDia5().getDateEditor().setDate(null);
			this.janelaPrincipal.getDataPanelDia6().setVisible(false);
			this.janelaPrincipal.getDataPanelDia6().setEnabled(false);
			this.janelaPrincipal.getDataPanelDia6().getDateEditor().setDate(null);
			this.janelaPrincipal.getComboBoxFimDia1().setVisible(true);
			this.janelaPrincipal.getComboBoxFimDia1().setEnabled(true);
			this.janelaPrincipal.getComboBoxFimDia2().setVisible(true);
			this.janelaPrincipal.getComboBoxFimDia2().setEnabled(true);
			this.janelaPrincipal.getComboBoxFimDia3().setVisible(true);
			this.janelaPrincipal.getComboBoxFimDia3().setEnabled(true);
			this.janelaPrincipal.getComboBoxFimDia4().setVisible(true);
			this.janelaPrincipal.getComboBoxFimDia4().setEnabled(true);
			this.janelaPrincipal.getComboBoxFimDia5().setVisible(false);
			this.janelaPrincipal.getComboBoxFimDia5().setEnabled(false);
			this.janelaPrincipal.getComboBoxFimDia5().setSelectedIndex(-1);
			this.janelaPrincipal.getComboBoxFimDia6().setVisible(false);
			this.janelaPrincipal.getComboBoxFimDia6().setEnabled(false);
			this.janelaPrincipal.getComboBoxFimDia6().setSelectedIndex(-1);

		} else if (this.janelaPrincipal.getComboBoxQtdDias().getSelectedItem().equals("5 Dias")) {

			this.janelaPrincipal.getComboBoxInicioDia1().setVisible(true);
			this.janelaPrincipal.getComboBoxInicioDia1().setEnabled(true);
			this.janelaPrincipal.getComboBoxInicioDia2().setVisible(true);
			this.janelaPrincipal.getComboBoxInicioDia2().setEnabled(true);
			this.janelaPrincipal.getComboBoxInicioDia3().setVisible(true);
			this.janelaPrincipal.getComboBoxInicioDia3().setEnabled(true);
			this.janelaPrincipal.getComboBoxInicioDia4().setVisible(true);
			this.janelaPrincipal.getComboBoxInicioDia4().setEnabled(true);
			this.janelaPrincipal.getComboBoxInicioDia5().setVisible(true);
			this.janelaPrincipal.getComboBoxInicioDia5().setEnabled(true);
			this.janelaPrincipal.getComboBoxInicioDia6().setVisible(false);
			this.janelaPrincipal.getComboBoxInicioDia6().setEnabled(false);
			this.janelaPrincipal.getComboBoxInicioDia6().setSelectedIndex(-1);
			this.janelaPrincipal.getDataPanelDia1().setVisible(true);
			this.janelaPrincipal.getDataPanelDia1().setEnabled(true);
			this.janelaPrincipal.getDataPanelDia2().setVisible(true);
			this.janelaPrincipal.getDataPanelDia2().setEnabled(true);
			this.janelaPrincipal.getDataPanelDia3().setVisible(true);
			this.janelaPrincipal.getDataPanelDia3().setEnabled(true);
			this.janelaPrincipal.getDataPanelDia4().setVisible(true);
			this.janelaPrincipal.getDataPanelDia4().setEnabled(true);
			this.janelaPrincipal.getDataPanelDia5().setVisible(true);
			this.janelaPrincipal.getDataPanelDia5().setEnabled(true);
			this.janelaPrincipal.getDataPanelDia6().setVisible(false);
			this.janelaPrincipal.getDataPanelDia6().setEnabled(false);
			this.janelaPrincipal.getDataPanelDia6().getDateEditor().setDate(null);
			this.janelaPrincipal.getComboBoxFimDia1().setVisible(true);
			this.janelaPrincipal.getComboBoxFimDia1().setEnabled(true);
			this.janelaPrincipal.getComboBoxFimDia2().setVisible(true);
			this.janelaPrincipal.getComboBoxFimDia2().setEnabled(true);
			this.janelaPrincipal.getComboBoxFimDia3().setVisible(true);
			this.janelaPrincipal.getComboBoxFimDia3().setEnabled(true);
			this.janelaPrincipal.getComboBoxFimDia4().setVisible(true);
			this.janelaPrincipal.getComboBoxFimDia4().setEnabled(true);
			this.janelaPrincipal.getComboBoxFimDia5().setVisible(true);
			this.janelaPrincipal.getComboBoxFimDia5().setEnabled(true);
			this.janelaPrincipal.getComboBoxFimDia6().setVisible(false);
			this.janelaPrincipal.getComboBoxFimDia6().setEnabled(false);
			this.janelaPrincipal.getComboBoxFimDia6().setSelectedIndex(-1);

		} else if (this.janelaPrincipal.getComboBoxQtdDias().getSelectedItem().equals("6 Dias")) {
			this.janelaPrincipal.getComboBoxInicioDia1().setVisible(true);
			this.janelaPrincipal.getComboBoxInicioDia1().setEnabled(true);
			this.janelaPrincipal.getComboBoxInicioDia2().setVisible(true);
			this.janelaPrincipal.getComboBoxInicioDia2().setEnabled(true);
			this.janelaPrincipal.getComboBoxInicioDia3().setVisible(true);
			this.janelaPrincipal.getComboBoxInicioDia3().setEnabled(true);
			this.janelaPrincipal.getComboBoxInicioDia4().setVisible(true);
			this.janelaPrincipal.getComboBoxInicioDia4().setEnabled(true);
			this.janelaPrincipal.getComboBoxInicioDia5().setVisible(true);
			this.janelaPrincipal.getComboBoxInicioDia5().setEnabled(true);
			this.janelaPrincipal.getComboBoxInicioDia6().setVisible(true);
			this.janelaPrincipal.getComboBoxInicioDia6().setEnabled(true);
			this.janelaPrincipal.getDataPanelDia1().setVisible(true);
			this.janelaPrincipal.getDataPanelDia1().setEnabled(true);
			this.janelaPrincipal.getDataPanelDia2().setVisible(true);
			this.janelaPrincipal.getDataPanelDia2().setEnabled(true);
			this.janelaPrincipal.getDataPanelDia3().setVisible(true);
			this.janelaPrincipal.getDataPanelDia3().setEnabled(true);
			this.janelaPrincipal.getDataPanelDia4().setVisible(true);
			this.janelaPrincipal.getDataPanelDia4().setEnabled(true);
			this.janelaPrincipal.getDataPanelDia5().setVisible(true);
			this.janelaPrincipal.getDataPanelDia5().setEnabled(true);
			this.janelaPrincipal.getDataPanelDia6().setVisible(true);
			this.janelaPrincipal.getDataPanelDia6().setEnabled(true);
			this.janelaPrincipal.getComboBoxFimDia1().setVisible(true);
			this.janelaPrincipal.getComboBoxFimDia1().setEnabled(true);
			this.janelaPrincipal.getComboBoxFimDia2().setVisible(true);
			this.janelaPrincipal.getComboBoxFimDia2().setEnabled(true);
			this.janelaPrincipal.getComboBoxFimDia3().setVisible(true);
			this.janelaPrincipal.getComboBoxFimDia3().setEnabled(true);
			this.janelaPrincipal.getComboBoxFimDia4().setVisible(true);
			this.janelaPrincipal.getComboBoxFimDia4().setEnabled(true);
			this.janelaPrincipal.getComboBoxFimDia5().setVisible(true);
			this.janelaPrincipal.getComboBoxFimDia5().setEnabled(true);
			this.janelaPrincipal.getComboBoxFimDia6().setVisible(true);
			this.janelaPrincipal.getComboBoxFimDia6().setEnabled(true);
		}
	} // limparCampos()

	public void abrirRequisacao(int i) {
		
		Formulario formulario = new Formulario();
		int idRequisicao[] = formularioDAO.retornaFormularioIdGeral(usuarioControle.getUsuarioId());
		int id = idRequisicao[i];
		
		if (id > 0) {
			if (formularioDAO.retornaRequisicao(id, formulario) && formularioDAO.retornaReserva(id, formulario)) {
				this.janelaPrincipal.getLabelInserirSolicitante().setText(formulario.getSolicitante());
				this.janelaPrincipal.getLabelInserirTelefone().setText(formulario.getTelefone());
				this.janelaPrincipal.getLabelInserirEmail().setText(formulario.getEmail());
				this.janelaPrincipal.getLabelInserirAtividade().setText(formulario.getAtividade());
				this.janelaPrincipal.getLabelInserirModalidade().setText(formulario.getModalidade());
				this.janelaPrincipal.getLabelInserirNalunos().setText(formulario.getNumeroAlunos() + "");
				this.janelaPrincipal.getLabelInserirEquipamentos().setText(formulario.getEquipamentos());
				this.janelaPrincipal.getLabelInserirCurso().setText(formulario.getCurso());
				this.janelaPrincipal.getLabelInserirData1().setText(formulario.getData(0));
				this.janelaPrincipal.getLabelInserirData2().setText(formulario.getData(1));
				this.janelaPrincipal.getLabelInserirData3().setText(formulario.getData(2));
				this.janelaPrincipal.getLabelInserirData4().setText(formulario.getData(3));
				this.janelaPrincipal.getLabelInserirData5().setText(formulario.getData(4));
				this.janelaPrincipal.getLabelInserirData6().setText(formulario.getData(5));
				this.janelaPrincipal.getLabelInserirHoraDia1().setText("Inicio: " + formulario.getHoraInicial(0)
						+ " horas - Fim: " + formulario.getHoraFinal(0) + " horas");
				this.janelaPrincipal.getLabelInserirHoraDia2().setText("Inicio: " + formulario.getHoraInicial(1)
						+ " horas - Fim: " + formulario.getHoraFinal(1) + " horas");
				this.janelaPrincipal.getLabelInserirHoraDia3().setText("Inicio: " + formulario.getHoraInicial(2)
						+ " horas - Fim: " + formulario.getHoraFinal(2) + " horas");
				this.janelaPrincipal.getLabelInserirHoraDia4().setText("Inicio: " + formulario.getHoraInicial(3)
						+ " horas - Fim: " + formulario.getHoraFinal(3) + " horas");
				this.janelaPrincipal.getlabelInserirHoraDia5().setText("Inicio: " + formulario.getHoraInicial(4)
						+ " horas - Fim: " + formulario.getHoraFinal(4) + " horas");
				this.janelaPrincipal.getlabelInserirHoraDia6().setText("Inicio: " + formulario.getHoraInicial(5)
						+ " horas - Fim: " + formulario.getHoraFinal(5) + " horas");

				if (formulario.getData(1) == null) {
					this.janelaPrincipal.getLabelInserirData2().setVisible(false);
					this.janelaPrincipal.getLabelData2AbrirFormulario().setVisible(false);
					this.janelaPrincipal.getLabelInserirHoraDia2().setVisible(false);

				} else {
					this.janelaPrincipal.getLabelInserirData2().setVisible(true);
					this.janelaPrincipal.getLabelData2AbrirFormulario().setVisible(true);
					this.janelaPrincipal.getLabelInserirHoraDia2().setVisible(true);
				}

				if (formulario.getData(2) == null) {
					this.janelaPrincipal.getLabelInserirData3().setVisible(false);
					this.janelaPrincipal.getLabelData3AbrirFormulario().setVisible(false);
					this.janelaPrincipal.getLabelInserirHoraDia3().setVisible(false);
				} else {
					this.janelaPrincipal.getLabelInserirData3().setVisible(true);
					this.janelaPrincipal.getLabelData3AbrirFormulario().setVisible(true);
					this.janelaPrincipal.getLabelInserirHoraDia3().setVisible(true);
				}
				
				if (formulario.getData(3) == null) {
					this.janelaPrincipal.getLabelInserirData4().setVisible(false);
					this.janelaPrincipal.getLabelData4AbrirFormulario().setVisible(false);
					this.janelaPrincipal.getLabelInserirHoraDia4().setVisible(false);
				} else {
					this.janelaPrincipal.getLabelInserirData4().setVisible(true);
					this.janelaPrincipal.getLabelData4AbrirFormulario().setVisible(true);
					this.janelaPrincipal.getLabelInserirHoraDia4().setVisible(true);
				}
				
				if (formulario.getData(4) == null) {
					this.janelaPrincipal.getLabelInserirData5().setVisible(false);
					this.janelaPrincipal.getLabelData5AbrirFormulario().setVisible(false);
					this.janelaPrincipal.getlabelInserirHoraDia5().setVisible(false);
				} else {
					this.janelaPrincipal.getLabelInserirData5().setVisible(true);
					this.janelaPrincipal.getLabelData5AbrirFormulario().setVisible(true);
					this.janelaPrincipal.getlabelInserirHoraDia5().setVisible(true);
				}
				
				if (formulario.getData(5) == null) {
					this.janelaPrincipal.getLabelInserirData6().setVisible(false);
					this.janelaPrincipal.getLabelData6AbrirFormulario().setVisible(false);
					this.janelaPrincipal.getlabelInserirHoraDia6().setVisible(false);
				} else {
					this.janelaPrincipal.getLabelInserirData6().setVisible(true);
					this.janelaPrincipal.getLabelData6AbrirFormulario().setVisible(true);
					this.janelaPrincipal.getlabelInserirHoraDia6().setVisible(true);
				}
				
			} else {
				this.janelaPrincipal.mensagemAbrirRequisicaoErro();
			}
		}

	} // abrirRequisacao()

	@Override
	public void actionPerformed(ActionEvent evento) {
		
		if (evento.getActionCommand().equals("Reservar")) {
			trocarPainel("painelInicioFormulario");
		}
		
		if (evento.getActionCommand().equals("VerReserva")) {
			exibirRequisicoes();
			trocarPainel("painelVerReserva");
		}
		
		if (evento.getActionCommand().equals("Finalizar")) {
			salvarRequisicao();
		}
		
		if (evento.getActionCommand().equals("OKdias")) {
			mostrarCamposDatas();
		}
		
		if (evento.getActionCommand().equals("Revisar")) {
			trocarPainel("painelInicioFormulario");
		}
		
		if (evento.getActionCommand().equals("Finalizar")) {
			limparCampos();
			trocarPainel("painelInicial");
		}

		if (evento.getActionCommand().equals("Abrir1")) {
			abrirRequisacao(0);
			trocarPainel("painelAbrirFormulario");
		}
		
		if (evento.getActionCommand().equals("Abrir2")) {
			abrirRequisacao(1);
			trocarPainel("painelAbrirFormulario");
		}
		
		if (evento.getActionCommand().equals("Abrir3")) {
			abrirRequisacao(2);
			trocarPainel("painelAbrirFormulario");
		}
		
		if (evento.getActionCommand().equals("Abrir4")) {
			abrirRequisacao(3);
			trocarPainel("painelAbrirFormulario");
		}
	
		if (evento.getActionCommand().equals("FecharPainelAbrirFormulario")) {
			exibirRequisicoes();
			trocarPainel("painelVerReserva");
		}
		
		if(evento.getActionCommand().equals("Excluir1")) {
			excluirRequisicao(0);
			exibirRequisicoes();
		}
		
		if(evento.getActionCommand().equals("Excluir2")) {
			excluirRequisicao(1);
			exibirRequisicoes();
		}
		
		if(evento.getActionCommand().equals("Excluir3")) {
			excluirRequisicao(2);
			exibirRequisicoes();
		}
		
		if(evento.getActionCommand().equals("Excluir4")) {
			excluirRequisicao(3);
			exibirRequisicoes();
		}

	} // actionPerformed()
	
	@Override
	public void mouseClicked(MouseEvent e) {
		
		if (e.getComponent() == this.janelaPrincipal.getLabelAvancarPainelInicioFormulario()) {
			if (validarCamposSolicitante()) {
				trocarPainel("painelData");
			} else {
				this.janelaPrincipal.mensagemCamposVazios();
			}
		}
		
		if (e.getComponent() == this.janelaPrincipal.getLabelVoltarPainelInicioFormulario()) {
			limparCampos();
			trocarPainel("painelInicial");
		}

		if (e.getComponent() == this.janelaPrincipal.getLabelVoltarPainelData()) {
			trocarPainel("painelInicioFormulario");

		}

		if (e.getComponent() == this.janelaPrincipal.getLabelAvancarPainelData()) {
			if (validarCamposData()) {
				trocarPainel("painelEquipamentos");
			} else {
				this.janelaPrincipal.mensagemCamposVazios();
			}
		}

		if (e.getComponent() == this.janelaPrincipal.getLabelVoltarEquipamentos()) {
			trocarPainel("painelData");

		}

		if (e.getComponent() == this.janelaPrincipal.getLabelAvancarEquipamentos()) {
			if (validarCamposEquipamentos()) {
				trocarPainel("painelConfirmarReserva");
			}
		}

		if (e.getComponent() == this.janelaPrincipal.getLabelVoltarVerReserva()) {
			trocarPainel("painelInicial");
		}

	} // mouseClicked()

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

}
