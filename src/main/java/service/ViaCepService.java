package service;

import com.google.gson.Gson;
import dto.EnderecoDto;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class ViaCepService {

    private static String urlBase = "https://viacep.com.br/ws/";
    private static String urlBaseTipo = "/json";

    public static EnderecoDto buscaEnderecoViaCep(String cep) {
        StringBuilder sb = new StringBuilder(urlBase).append(cep).append(urlBaseTipo);
        try {
            URL url = new URL(sb.toString());
            HttpURLConnection conexao = (HttpURLConnection) url.openConnection();
            conexao.setRequestProperty("User-Agent", "Mozilla/5.0");
            conexao.setRequestProperty("Accept-Charset", "UTF-8");
            conexao.setRequestProperty("Accept", "application/json");
            conexao.setRequestProperty("Content-Type", "application/json;charset=UTF-8");

            if (conexao.getResponseCode() != 200)
                throw new RuntimeException("HTTP error code : " + conexao.getResponseCode());

            BufferedReader resposta = new BufferedReader(new InputStreamReader(conexao.getInputStream(), StandardCharsets.UTF_8));
            Gson gson = new Gson();

            return gson.fromJson(resposta, EnderecoDto.class);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
