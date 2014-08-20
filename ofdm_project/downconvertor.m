function [ Down_Symbol ] = downconvertor ( Filter_Data )

Down_Symbol = zeros(548,140);


counter=1;

for m = 1:4:2189
    Down_Symbol(counter,:) = Filter_Data(m,:);
    counter=counter+1;
end
end
