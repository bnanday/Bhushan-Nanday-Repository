function [ random_bits ] = randomdatagenerator( )

% If QPSK modulation is used then 80000 bits are generated as we need 40000
% QPSK symbols
% If 16-QAM modulation is used then 160000 bits are generated as we need
% 40000 16-QAM symbols
% Variable mod_type recognises the type of modulation scheme choosen by the user.


random_bits = randi([0,1],1,160000);


end




