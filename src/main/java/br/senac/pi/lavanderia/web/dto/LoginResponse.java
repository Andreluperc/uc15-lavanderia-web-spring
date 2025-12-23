package br.senac.pi.lavanderia.web.dto;

public class LoginResponse {
    private boolean ok;
    private String message;

    public LoginResponse() {}
    public LoginResponse(boolean ok, String message) {
        this.ok = ok;
        this.message = message;
    }

    public boolean isOk() { return ok; }
    public void setOk(boolean ok) { this.ok = ok; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
}
