package com.ConsultaAutomovel.ConsultaAutomovel.principal;

import com.ConsultaAutomovel.ConsultaAutomovel.Service.ConsumoAPI;
import com.ConsultaAutomovel.ConsultaAutomovel.Service.ConverterDados;
import com.ConsultaAutomovel.ConsultaAutomovel.models.Dados;
import com.ConsultaAutomovel.ConsultaAutomovel.models.Modelos;
import com.ConsultaAutomovel.ConsultaAutomovel.models.Veiculo;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Principal {
    Scanner leitura = new Scanner(System.in);
    private final ConsumoAPI consumo = new ConsumoAPI();
    private final ConverterDados conversor = new ConverterDados();
    private final String URL_BASE = "https://parallelum.com.br/fipe/api/v1/";


    public void exibirMenu() throws JsonProcessingException {

       var menu = """
               *** Opções ***
               Carro
               Moto 
               Caminhão
               
               Digite uma das opções para consultar:
               """;

       System.out.println(menu);
       var opcao = leitura.nextLine();

       String endereco;

       if (opcao.toLowerCase().contains("carr")){
            endereco = URL_BASE + "carros/marcas";
       }
        else if (opcao.toLowerCase().contains("mot")){
            endereco = URL_BASE + "motos/marcas";
        }
        else {
            endereco = URL_BASE + "caminhoes/marcas";
        }
         var json = consumo.obterDados(endereco);
        System.out.println(json);
        var marcas = conversor.obterLista(json, Dados.class);
        marcas.stream()
                .sorted(Comparator.comparing(Dados::codigo))
                .forEach(System.out::println);

        System.out.println("Informe a marca que você deseja consultar: ");
        var codigoMarca = leitura.nextLine();


        endereco = endereco + "/" + codigoMarca + "/modelos";
        json = consumo.obterDados(endereco);
        var modeloLista = conversor.obterDados(json, Modelos.class);

        System.out.println("\nModelos dessa marca:");
        modeloLista.modelos().stream()
                .sorted(Comparator.comparing(Dados::codigo))
                .forEach(System.out::println);

        System.out.println("\nDigite qual veículo deseja procurar: ");
        var nomeVeiculo = leitura.nextLine();

        List<Dados> modelosFiltrados = modeloLista.modelos().stream()
                .filter(m -> m.nome().toLowerCase().contains(nomeVeiculo.toLowerCase()))
                .collect(Collectors.toList());
        System.out.println("\nModelos filtrados:");
        modelosFiltrados.forEach(System.out::println);

        System.out.println("Digite o código do modelo que deseja consultar: ");
        var codigoModelo = leitura.nextLine();

        endereco = endereco + "/" + codigoModelo + "/anos";
        json = consumo.obterDados(endereco);
        List<Dados> anos = conversor.obterLista(json, Dados.class);
        List<Veiculo> veiculos = new ArrayList<>();

        for (int i = 0; i < anos.size(); i++){
            var enderecoAnos = endereco + "/" + anos.get(i).codigo();
            json = consumo.obterDados(enderecoAnos);
            Veiculo veiculo = conversor.obterDados(json, Veiculo.class);
            veiculos.add(veiculo);
        }
        System.out.println("\nTodos os veículos por ano:");
        veiculos.forEach(System.out::println);

        for (int i = 2; i <= veiculos.size() ; i++) {

            System.out.println("\nDeseja consultar um veículo em particular?");
            var opcaoInformacao = leitura.nextLine();


            if (opcaoInformacao.toLowerCase().equals("sim")) {
                System.out.println("\nSelecione o veículo que deseja consultar digitando o ano:");
                var veiculoSelecionado = leitura.nextInt();

                String enderecoVeiculo = endereco + "/" + veiculoSelecionado + "-1";
                json = consumo.obterDados(enderecoVeiculo);
                Veiculo veiculo = conversor.obterDados(json, Veiculo.class);

                System.out.println("\nInformações do veículo selecionado:");
                System.out.println(veiculo);
            } else {

            }
        }

    }
}



