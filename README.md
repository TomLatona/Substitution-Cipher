# Substitution-Cipher
A Java program that can encrypt and decrypt files using a custom substitution cipher key generator.
The user will enter letters to pair with every letter of the alphabet, then save the key and use it to encrypt a plaintext message. When the key file and encrypted file are sent to a friend they can use them both to decrypt the message instantly.
# Cryptanalysis attacker program
This program will accept the encrypted file created from the Subtitution-Cipher program, and run a letter frequency cryptanalysis attack by calculating the percentage each letter appears in the text, and then cross referencing those values with a reference library of the actual percentages that letters show up in the Oxford English dictionary.
It will also use these values to create a partially decrypted version of the encrypted text by swapping characters with a frequency percentage within a small range. One can use the list of percentages to further decrypt the message.
