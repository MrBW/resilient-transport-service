package de.codecentric.resilient.dto;

/**
 * Booking Request DTO
 * @author Benjamin Wilms
 */
public class BookingServiceRequestDTO extends AbstractDTO {

    private CustomerResponseDTO customerDTO;

    private AddressResponseDTO senderAddress;

    private AddressResponseDTO receiverAddress;

    public CustomerResponseDTO getCustomerDTO() {
        return customerDTO;
    }

    public void setCustomerDTO(CustomerResponseDTO customerDTO) {
        this.customerDTO = customerDTO;
    }

    public AddressResponseDTO getSenderAddress() {
        return senderAddress;
    }

    public void setSenderAddress(AddressResponseDTO senderAddress) {
        this.senderAddress = senderAddress;
    }

    public AddressResponseDTO getReceiverAddress() {
        return receiverAddress;
    }

    public void setReceiverAddress(AddressResponseDTO receiverAddress) {
        this.receiverAddress = receiverAddress;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("BookingServiceRequestDTO{");
        sb.append("customerDTO=").append(customerDTO);
        sb.append(", senderAddress=").append(senderAddress);
        sb.append(", receiverAddress=").append(receiverAddress);
        sb.append('}');
        return sb.toString();
    }
}
