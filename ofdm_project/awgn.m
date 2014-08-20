function [ channel_output ] = awgn( channel_output,snr_db )

Fs=(4*7.68*(10^6));
Ts=1/Fs;
E = 0;
signal=abs(channel_output).^2;
for i=1:length(signal)
E=E+signal(i);
end

Eb = E/(length(channel_output)*16.4*10^6);
Eb_by_No = 10^(snr_db/10);
No = Eb/Eb_by_No;

Pn = No*Fs/2;

awgn = sqrt(Pn)* randn(1,length(channel_output));

channel_output = channel_output + awgn;

end

